import { useEffect, useState } from 'react';

import { useAuth } from '@context/AuthProvider';
import { useOrderActions } from '@hooks';
import { Orders as OrderComponent, Sidebar, SplitView } from '@components';
import Order from 'types/Order';

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
      <OrderComponent orders={orders} />
    </SplitView>
  );
};

export default Orders;
