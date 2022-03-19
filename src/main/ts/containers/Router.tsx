import { useRoutes } from 'react-router-dom';
import {
  OrderDashboard, DataDashboard, Dataset, Landing, Login, Marketplace, Register, Account, Orders,
} from '@views';
// import { useAuth } from '@context/AuthProvider';

const Router = () => {
  // const { user } = useAuth();

  const routes = useRoutes([
    { path: '/', element: <Landing /> },
    { path: '/login', element: <Login /> },
    { path: '/register', element: <Register /> },
    { path: '/account', element: <Account /> },
    { path: '/orderdashboard', element: <OrderDashboard /> },
    { path: '/datadashboard', element: <DataDashboard /> },
    { path: '/dataset', element: <Dataset /> },
    { path: '/marketplace', element: <Marketplace /> },
    { path: '/orders', element: <Orders /> },
  ]);

  return routes;
};

export default Router;
