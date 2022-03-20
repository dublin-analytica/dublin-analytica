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
  Dataset,
} from '@views';
// import { useAuth } from '@context/AuthProvider';

const Router = () => {
  // const { user } = useAuth();

  const routes = useRoutes([
    { path: '/', element: <Landing /> },
    { path: '/login', element: <Login /> },
    { path: '/register', element: <Register /> },
    { path: '/account', element: <Account /> },

    { path: '/dashboard/orders', element: <OrderDashboard /> },
    { path: '/dashboard/data', element: <DataDashboard /> },

    { path: '/marketplace', element: <Marketplace /> },
    { path: '/orders', element: <Orders /> },
    { path: '/404', element: <Page404 /> },
    { path: '/basket', element: <Basket /> },

    { path: '/dataset/add', element: <AddDataset /> },
    { path: '/dataset/:id/edit', element: <EditDataset /> },
    { path: '/dataset/:id', element: <Dataset /> },

    { path: '*', element: <Page404 /> },
  ]);

  return routes;
};

export default Router;
