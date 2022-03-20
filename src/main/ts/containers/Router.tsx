import { useRoutes } from 'react-router-dom';
import {
  OrderDashboard,
  DataDashboard,
  Landing,
  Login,
  Marketplace,
  Register,
  Account,
  Orders,
  Basket,
  Page404,
  AddDataset,
  EditDataset,
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
    { path: '/marketplace', element: <Marketplace /> },
    { path: '/orders', element: <Orders /> },
    { path: '/404', element: <Page404 /> },
    { path: '/basket', element: <Basket /> },
    { path: '/adddataset', element: <AddDataset /> },
    { path: '/editdataset', element: <EditDataset /> },
    { path: '*', element: <Page404 /> },
  ]);

  return routes;
};

export default Router;
