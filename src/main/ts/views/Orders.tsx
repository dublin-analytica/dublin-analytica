import { useEffect, useState } from 'react';

import { useAuth } from '@context/AuthProvider';
import { useOrderActions } from '@hooks';
import Order from 'types/Order';

import SplitView from '../components/SplitView';
import OrderTable from '../components/OrderTable';
import Sidebar from '../components/Sidebar';

const Orders = () => {
  const [orders, setOrders] = useState([] as Order[]);

  const { user } = useAuth();
  const { getOrders } = useOrderActions();

  useEffect(() => {
    getOrders(user?.id).then(setOrders);
  }, []);

  return (
    <SplitView>
      <Sidebar />
      <OrderTable orders={orders} />
    </SplitView>
  );
};

export default Orders;
