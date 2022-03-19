import { Navigate, useRoutes } from 'react-router-dom';
import {
  Dashboard, Dataset, Landing, Login, Marketplace, Register, Account, Orders,
} from '@views';
import { useAuth } from '@context/AuthProvider';

const Router = () => {
  const { user } = useAuth();

  const routes = useRoutes([
    { path: '/', element: <Landing /> },
    { path: '/login', element: user ? <Navigate to="/account" replace /> : <Login /> },
    { path: '/register', element: user ? <Navigate to="/account" replace /> : <Register /> },
    { path: '/account', element: user ? <Account /> : <Navigate to="/login" replace /> },
    { path: '/dashboard', element: user?.admin ? <Dashboard /> : <Navigate to="/account" replace /> },
    { path: '/dataset', element: <Dataset /> },
    { path: '/marketplace', element: <Marketplace /> },
    { path: '/orders', element: user ? <Orders /> : <Navigate to="/login" replace /> },
  ]);

  return routes;
};

export default Router;
