import { useState } from 'react';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Label } from './ui/label';
import { Card, CardContent } from './ui/card';
import { ImageWithFallback } from './ui/ImageWithFallback';
import {
  Database,
  Download,
  Shield,
  LogOut,
  Terminal,
  FileSpreadsheet,
  Lock,
  Activity
} from 'lucide-react';

interface SecretPageProps {
  onLogout: () => void;
}

export function SecretPage({ onLogout }: SecretPageProps) {
  const [password, setPassword] = useState('');
  const [activeAction, setActiveAction] = useState<string | null>(null);
  const [statusMessage, setStatusMessage] = useState<string | null>(null);

  const handleAction = async (action: string) => {
    setActiveAction(action);
    setStatusMessage(null);

    try {
      let url = '';
      let options: RequestInit = {};

      if (action === 'ПАРСИТЬ ТОП 25 IMDB') {
        url = 'http://localhost:8080/api/imdb/parse';
        options.method = 'POST';
      } else if (action === 'СКАЧАТЬ EXCEL') {
        url = 'http://localhost:8080/api/imdb/excel';
        options.method = 'GET';
      } else if (action === 'СКАЧАТЬ ЗАШИФРОВАННЫЙ EXCEL') {
        if (!password) {
          alert('Введите пароль для шифрования!');
          setActiveAction(null);
          return;
        }
        url = `http://localhost:8080/api/imdb/excel/protected?password=${encodeURIComponent(password)}`;
        options.method = 'GET';
      }

      const response = await fetch(url, options);

      if (!response.ok) throw new Error(`Ошибка сервера: ${response.status}`);

      if (action === 'ПАРСИТЬ ТОП 25 IMDB') {
        const data = await response.json();
        setStatusMessage(`✅ Успешно спарсили ${data.length} фильмов!`);
      } else {
        // скачивание Excel
        const blob = await response.blob();
        const filename = action.includes('ЗАШИФРОВАННЫЙ') ? 'imdb_encrypted.xlsx.aes' : 'imdb_top25.xlsx';
        const downloadUrl = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = downloadUrl;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        a.remove();
        URL.revokeObjectURL(downloadUrl);
        setStatusMessage(`✅ Файл ${filename} успешно скачан!`);
      }
    } catch (err: any) {
      console.error(err);
      setStatusMessage(`❌ Ошибка при выполнении: ${err.message}`);
    } finally {
      setActiveAction(null);
    }
  };

  return (
      <div className="min-h-screen bg-black text-green-400 font-mono relative overflow-hidden">
        {/* Matrix-style background */}
        <div className="absolute inset-0 opacity-10">
          <div className="absolute inset-0" style={{
            backgroundImage: `url("data:image/svg+xml,%3Csvg width='40' height='40' viewBox='0 0 40 40' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='%2300ff00' fill-opacity='0.1'%3E%3Cpath d='M20 20h20v20H20z'/%3E%3C/g%3E%3C/svg%3E")`
          }}></div>
        </div>

        {/* Header */}
        <div className="relative z-10 flex justify-between items-center p-6 border-b border-green-500/30">
          <div className="flex items-center gap-3">
            <Terminal className="w-8 h-8 text-lime-400" />
            <div>
              <h1 className="text-2xl text-lime-400">CLASSIFIED SYSTEM</h1>
              <div className="text-sm text-green-300 flex items-center gap-2">
                <Activity className="w-3 h-3 animate-pulse" />
                SECURE CONNECTION ESTABLISHED
              </div>
            </div>
          </div>
          <Button
              onClick={onLogout}
              variant="outline"
              className="border-red-500 text-red-400 hover:bg-red-500/20 hover:text-red-300"
          >
            <LogOut className="w-4 h-4 mr-2" />
            DISCONNECT
          </Button>
        </div>

        <div className="relative z-10 container mx-auto p-6 space-y-8">
          {/* Main Title */}
          <div className="text-center space-y-4">
            <h2 className="text-4xl md:text-6xl text-lime-400 tracking-wider animate-pulse">
              ///СЕКРЕТНАЯ ИНФА///
            </h2>
            <div className="text-green-300 text-lg">
              &gt; ACCESS LEVEL: TOP SECRET &lt;
            </div>
          </div>

          {/* Secret Image */}
          <Card className="bg-green-900/20 border-green-500/30 overflow-hidden">
            <CardContent className="p-6">
              <div className="text-center space-y-4">
                <h3 className="text-xl text-lime-400">[CLASSIFIED DOCUMENT]</h3>
                <div className="relative mx-auto max-w-md">
                  <ImageWithFallback
                      src="https://images.unsplash.com/photo-1610758758876-0680d8c2247c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzZWNyZXQlMjBoYWNrZXIlMjBjb21wdXRlciUyMGNvZGV8ZW58MXx8fHwxNzU4NzQwMzUxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
                      alt="Secret hacker data"
                      className="w-full h-64 object-cover rounded-lg border-2 border-green-500/50 shadow-lg shadow-green-500/20"
                  />
                  <div className="absolute inset-0 bg-green-500/10 rounded-lg"></div>
                </div>
                <div className="text-green-300 text-sm">
                  &gt; BIOMETRIC SCAN REQUIRED FOR FULL ACCESS &lt;
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Action Buttons */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <Button
                onClick={() => handleAction('ПАРСИТЬ ТОП 25 IMDB')}
                disabled={activeAction === 'ПАРСИТЬ ТОП 25 IMDB'}
                className="h-16 bg-gradient-to-r from-green-600 to-lime-500 hover:from-green-500 hover:to-lime-400 text-black border-2 border-green-400 shadow-lg shadow-green-500/25 transition-all duration-300"
            >
              <div className="flex items-center gap-3">
                <Database className="w-6 h-6" />
                <div className="text-left">
                  <div className="text-sm">EXECUTE</div>
                  <div>СПАРСИТЬ ТОП 25 IMDB</div>
                </div>
              </div>
            </Button>

            <Button
                onClick={() => handleAction('СКАЧАТЬ EXCEL')}
                disabled={activeAction === 'СКАЧАТЬ EXCEL'}
                className="h-16 bg-gradient-to-r from-blue-600 to-cyan-500 hover:from-blue-500 hover:to-cyan-400 text-black border-2 border-cyan-400 shadow-lg shadow-cyan-500/25 transition-all duration-300"
            >
              <div className="flex items-center gap-3">
                <Download className="w-6 h-6" />
                <div className="text-left">
                  <div className="text-sm">DOWNLOAD</div>
                  <div>СКАЧАТЬ EXCEL</div>
                </div>
              </div>
            </Button>

            <Button
                onClick={() => handleAction('СКАЧАТЬ ЗАШИФРОВАННЫЙ EXCEL')}
                disabled={activeAction === 'СКАЧАТЬ ЗАШИФРОВАННЫЙ EXCEL'}
                className="h-16 bg-gradient-to-r from-purple-600 to-pink-500 hover:from-purple-500 hover:to-pink-400 text-black border-2 border-purple-400 shadow-lg shadow-purple-500/25 transition-all duration-300"
            >
              <div className="flex items-center gap-3">
                <Shield className="w-6 h-6" />
                <div className="text-left">
                  <div className="text-sm">SECURE</div>
                  <div className="text-xs">СКАЧАТЬ ЗАШИФРОВАННЫЙ EXCEL</div>
                </div>
              </div>
            </Button>
          </div>

          {/* Status Display */}
          {statusMessage && (
              <Card className="bg-lime-400/10 border-lime-400/50">
                <CardContent className="p-4">
                  <div className="flex items-center gap-3 text-lime-400">
                    <div className="w-4 h-4 border-2 border-lime-400 border-t-transparent rounded-full animate-spin"></div>
                    <div>{statusMessage}</div>
                  </div>
                </CardContent>
              </Card>
          )}

          {/* Password Field */}
          <Card className="bg-red-900/20 border-red-500/30">
            <CardContent className="p-6 space-y-4">
              <div className="flex items-center gap-3 text-red-400">
                <Lock className="w-5 h-5" />
                <h3 className="text-lg">RESTRICTED ACCESS ZONE</h3>
              </div>
              <div className="space-y-2">
                <Label className="text-red-300 flex items-center gap-2">
                  <FileSpreadsheet className="w-4 h-4" />
                  Encryption Password
                </Label>
                <Input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Enter decryption key..."
                    className="bg-black/50 border-red-500/50 text-red-200 placeholder:text-red-400/50 focus:border-red-400 focus:ring-red-400/20"
                />
              </div>
              <div className="text-xs text-red-300/70">
                &gt; WARNING: Unauthorized access is monitored and logged
              </div>
            </CardContent>
          </Card>

          {/* Footer */}
          <div className="text-center text-green-500/50 text-sm space-y-2">
            <div>[ SYSTEM UPTIME: 99.97% ] [ THREATS DETECTED: 0 ] [ FIREWALLS: ACTIVE ]</div>
            <div>&gt;&gt;&gt; ALL ACTIVITIES ARE MONITORED AND RECORDED &lt;&lt;&lt;</div>
          </div>
        </div>
      </div>
  );
}