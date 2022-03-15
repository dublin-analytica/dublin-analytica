import { useAuth } from '@context/AuthProvider';
import useAPI from './useAPI';

const useAuthActions = () => {
  const { post } = useAPI();
  const { setToken, removeToken } = useAuth();

  const login = async (email: string, password: string) => {
    post('users/login', { email, password })
      .then(({ token }) => {
        console.log(token);
        setToken(token);
      });
  };

  const logout = async () => {
    post('users/logout');
    removeToken();
  };

  const register = async (name: string, email: string, password: string) => {
    await post('users/register', { name, email, password });
    login(email, password);
  };

  return { login, logout, register };
};

export default useAuthActions;
