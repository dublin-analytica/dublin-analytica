import { useRoutes } from 'react-router-dom';
import {
  OrderDashboard,
  DataDashboard,
  Dataset,
  Landing,
  Login,
  Marketplace,
  Register,
  Account,
  Orders,
  Basket,
  Page404,
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
    { path: '/404', element: <Page404 /> },
    { path: '/basket', element: <Basket /> },
  ]);

  return routes;
};

export default Router;
