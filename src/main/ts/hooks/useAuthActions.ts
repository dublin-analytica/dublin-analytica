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

  const update = async (name: string, email: string) => (
    post('users/me', { name, email })
      .then(({ token }) => setToken(token))
  );

  const changePassword = async (oldPassword: string, newPassword: string) => (
    post('users/me/password', { oldPassword, newPassword })
      .then(({ token }) => setToken(token))
  );

  return {
    login, logout, register, update, changePassword,
  };
};

export default useAuthActions;
