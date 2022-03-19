import { useEffect, useState } from 'react';
// import { useNavigate } from 'react-router-dom';

import {
  Orders, Sidebar, SplitView, Stats,
} from '@components';
import { useOrderActions } from '@hooks';
// import { useAuth } from '@context/AuthProvider';

import Order from 'types/Order';
import Status from 'types/Status';

const Dashboard = () => {
  const [orders, setOrders] = useState([] as Order[]);

  // const navigate = useNavigate();
  // const { user } = useAuth();
  const { getOrders } = useOrderActions();

  useEffect(() => {
    getOrders().then((orders) => setOrders(orders));
  }, []);

  // useEffect(() => {
  //   if (!user?.admin) navigate('/404');
  // }, []);

  const stats = [
    { name: 'Revenue', value: `€${orders.reduce((acc, order) => acc + order.price, 0)}` },
    { name: 'Total Orders', value: orders.length.toString() },
    { name: 'Pending Orders', value: orders.filter((order) => order.status !== Status.DELIVERED).length.toString() },
  ];

  return (
    <SplitView>
      <Sidebar />
      <>
        <Stats stats={stats} />
        <Orders orders={orders} />
      </>
    </SplitView>
  );
};

export default Dashboard;
