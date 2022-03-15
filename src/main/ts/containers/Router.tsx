import { useRoutes } from 'react-router-dom';
import {
  Dashboard, Dataset, Landing, Login, Marketplace, Register,
} from '@views';

const Router = () => {
  const routes = useRoutes([
    { path: '/', element: <Landing /> },
    { path: '/login', element: <Login /> },
    { path: '/register', element: <Register /> },
    { path: '/dashboard', element: <Dashboard /> },
    { path: '/dataset', element: <Dataset /> },
    { path: '/marketplace', element: <Marketplace /> },
  ]);

  return routes;
};

export default Router;
