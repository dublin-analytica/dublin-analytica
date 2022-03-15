import React, { useContext, useEffect, useMemo } from 'react';
import jwtDecode from 'jwt-decode';

type AuthProviderProps = { children: React.ReactNode };

export type User = {
  id: string;
  name: string;
  email: string;
} | null

type GetToken = () => string | null;
type SetToken = (token: string) => void;
type RemoveToken = () => void;

type Context = {
  user: User | null,
  getToken: GetToken,
  setToken: SetToken,
  removeToken: RemoveToken
};

const AuthContext = React.createContext<Context | null>(null);

const AuthProvider = ({ children }: AuthProviderProps) => {
  const [user, setUser] = React.useState<User | null>(null);

  const getToken = () => localStorage.getItem('token');

  const removeToken = () => {
    localStorage.removeItem('token');
    setUser(null);
  };

  const setUserFromToken = (token: string) => {
    try {
      const decoded = jwtDecode(token);
      console.log(decoded);
      setUser(decoded as User);
      console.log(`user: ${JSON.stringify(user)}`);
    } catch (error) {
      console.log(error);
      removeToken();
    }
  };

  const setToken = (token: string) => {
    localStorage.setItem('token', token);
    setUserFromToken(token);
  };

  useEffect(() => {
    const token = getToken();
    if (token) setUserFromToken(token);
  }, []);

  const context = useMemo(() => ({
    user, getToken, setToken, removeToken,
  }), [user]);

  return (
    <AuthContext.Provider value={context}>
      {children}
    </AuthContext.Provider>
  );
};

const useAuth = (): Context => {
  const context = useContext(AuthContext);
  if (!context) throw Error('useAuth must be used within a AuthProvider');
  return context;
};

export { AuthProvider, useAuth };
