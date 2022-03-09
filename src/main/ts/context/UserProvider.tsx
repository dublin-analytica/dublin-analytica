import React, { useContext, useMemo } from 'react';

type UserProviderProps = { children: React.ReactNode };

type User = {
  id: string;
  name: string;
  email: string;
}

const UserContext = React.createContext<User | null>(null);

const UserProvider = ({ children }: UserProviderProps) => {
  const user = useMemo(() => ({
    id: '0',
    name: 'Evan Brierton',
    email: 'evan@brierton.ie',
  }), []);

  return (
    <UserContext.Provider value={user}>
      {children}
    </UserContext.Provider>
  );
};

const useUser = (): User | null => {
  const context = useContext(UserContext);
  return context;
};

export { UserProvider, useUser };
