import React, { useContext, useEffect, useMemo } from 'react';
import jwtDecode from 'jwt-decode';

import type User from 'types/User';

type AuthProviderProps = { children: React.ReactNode };

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
      setUser(decoded as User);
    } catch (error) {
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
