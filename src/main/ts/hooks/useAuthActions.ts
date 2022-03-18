import { useNavigate } from 'react-router-dom';

import { useAuth } from '@context/AuthProvider';
import useAPI from './useAPI';

const useAuthActions = () => {
  const navigate = useNavigate();
  const { post } = useAPI();
  const { setToken, removeToken } = useAuth();

  const login = async (email: string, password: string) => (
    post('users/login', { email, password })
      .then(({ token }) => setToken(token))
  );

  const logout = async () => {
    try {
      await post('users/logout');
    } catch (error) {
      console.log(error);
    }

    removeToken();
    navigate('/login');
  };

  const register = async (name: string, email: string, password: string) => (
    post('users/register', { name, email, password })
      .then(() => login(email, password))
  );

  return { login, logout, register };
};

export default useAuthActions;
