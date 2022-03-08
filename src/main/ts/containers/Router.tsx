import { useRoutes } from 'react-router-dom';
import {
  Dashboard, Dataset, Landing, Login, Marketplace, Signup,
} from '../views';

const Router = () => {
  const routes = useRoutes([
    { path: '/', element: <Landing /> },
    { path: '/login', element: <Login /> },
    { path: '/signup', element: <Signup /> },
    { path: '/dashboard', element: <Dashboard /> },
    { path: '/dataset', element: <Dataset /> },
    { path: '/marketplace', element: <Marketplace /> },
  ]);

  return routes;
};

export default Router;
