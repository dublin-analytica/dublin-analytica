import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { Orders, SplitView, Stats } from '@components';
import { useOrderActions } from '@hooks';
import { useAuth } from '@context/AuthProvider';

import Order from 'types/Order';

const Dashboard = () => {
  const [orders, setOrders] = useState([] as Order[]);

  const navigate = useNavigate();
  const { user } = useAuth();
  const { getOrders } = useOrderActions();

  useEffect(() => {
    getOrders().then(setOrders);
    return () => {
      setOrders([]);
    };
  }, []);

  useEffect(() => {
    if (!user?.admin) navigate('/404');
  }, []);

  return (
    <SplitView>
      <h1>Dashboard</h1>
      <>
        <Stats stats={[]} />
        <Orders orders={orders} />
      </>
    </SplitView>
  );
};

export default Dashboard;
