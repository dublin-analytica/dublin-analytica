import { useNavigate } from 'react-router-dom';

import { useAuth } from '@context/AuthProvider';
import useAPI from './useAPI';

const useAuthActions = () => {
  const navigate = useNavigate();

  const { post } = useAPI();
  const { setToken, removeToken } = useAuth();

  const login = async (email: string, password: string) => {
    await post('users/login', { email, password }).then(setToken);
    navigate('/marketplace');
  };

  const logout = async () => {
    await post('users/logout');
    removeToken();
    navigate('/');
  };

  const register = async (name: string, email: string, password: string) => {
    await post('users/register', { name, email, password });
    login(email, password);
  };

  return { login, logout, register };
};

export default useAuthActions;
