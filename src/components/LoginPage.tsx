import { useState } from 'react';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Label } from './ui/label';
import { Card, CardContent, CardHeader, CardDescription, CardTitle } from './ui/card';
import { Alert, AlertDescription } from './ui/alert';
import { Lock, User, LogIn } from 'lucide-react';

interface LoginPageProps {
  onLogin: (username: string, password: string) => void;
  error: string | null;
  isLoading: boolean;
}

export function LoginPage({ onLogin, error, isLoading }: LoginPageProps) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onLogin(username, password);
  };

  return (
      <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 via-slate-800 to-black p-4">
        <div className="absolute inset-0 opacity-50">
          <div className="absolute inset-0" style={{
            backgroundImage: `url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23ffffff' fill-opacity='0.02'%3E%3Ccircle cx='30' cy='30' r='1'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E")`
          }}></div>
        </div>

        <Card className="w-full max-w-md bg-white/10 backdrop-blur-lg border-white/20 shadow-2xl">
          <CardHeader className="text-center space-y-4">
            <div className="mx-auto w-16 h-16 bg-gradient-to-br from-lime-400 to-green-500 rounded-full flex items-center justify-center shadow-lg">
              <Lock className="w-8 h-8 text-black" />
            </div>
            <CardTitle className="text-2xl text-white">Secure Access</CardTitle>
            <CardDescription className="text-gray-300">
              Enter your credentials to access the secret system
            </CardDescription>
          </CardHeader>

          <CardContent className="space-y-6">
            {error && (
                <Alert className="bg-red-500/20 border-red-500/50 text-red-200">
                  <AlertDescription>{error}</AlertDescription>
                </Alert>
            )}

            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="username" className="text-gray-200 flex items-center gap-2">
                  <User className="w-4 h-4" />
                  Username
                </Label>
                <Input
                    id="username"
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="bg-white/10 border-white/20 text-white placeholder:text-gray-400 focus:border-lime-400 focus:ring-lime-400/20"
                    placeholder="Enter your username"
                    required
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="password" className="text-gray-200 flex items-center gap-2">
                  <Lock className="w-4 h-4" />
                  Password
                </Label>
                <Input
                    id="password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="bg-white/10 border-white/20 text-white placeholder:text-gray-400 focus:border-lime-400 focus:ring-lime-400/20"
                    placeholder="Enter your password"
                    required
                />
              </div>

              <Button
                  type="submit"
                  disabled={isLoading || !username || !password}
                  className="w-full bg-gradient-to-r from-lime-400 to-green-500 hover:from-lime-500 hover:to-green-600 text-black transition-all duration-300 shadow-lg hover:shadow-lime-400/25 disabled:opacity-50"
              >
                {isLoading ? (
                    <div className="flex items-center gap-2">
                      <div className="w-4 h-4 border-2 border-black/30 border-t-black rounded-full animate-spin"></div>
                      Authenticating...
                    </div>
                ) : (
                    <div className="flex items-center gap-2">
                      <LogIn className="w-4 h-4" />
                      Access System
                    </div>
                )}
              </Button>
            </form>
          </CardContent>
        </Card>
      </div>
  );
}