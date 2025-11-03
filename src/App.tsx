import { useState } from 'react';
import { LoginPage } from './components/LoginPage';
import { SecretPage } from './components/SecretPage';

interface User {
  username: string;
  isAuthenticated: boolean;
}

export default function App() {
  const [user, setUser] = useState<User | null>(null);
  const [loginError, setLoginError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = async (username: string, password: string) => {
    setIsLoading(true);
    setLoginError(null);

    try {
      const response = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });

      const data = await response.json();

      if (data.success) {
        setUser({ username, isAuthenticated: true });
      } else {
        setLoginError(data.message || 'Login failed');
      }
    } catch (err) {
      console.error(err);
      setLoginError('Server error. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  const handleLogout = () => {
    setUser(null);
    setLoginError(null);
  };

  if (user?.isAuthenticated) {
    return <SecretPage onLogout={handleLogout} />;
  }

  return <LoginPage onLogin={handleLogin} error={loginError} isLoading={isLoading} />;
}