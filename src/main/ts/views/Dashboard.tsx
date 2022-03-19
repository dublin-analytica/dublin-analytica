import React, { useEffect, useState } from 'react';
// import { useNavigate } from 'react-router-dom';

import {
  Button,
  Orders, Sidebar, SplitView, Stats,
} from '@components';
import { useOrderActions } from '@hooks';
// import { useAuth } from '@context/AuthProvider';

import Order from 'types/Order';
import Status from 'types/Status';
import { Container } from '@containers';

import { toTitleCase } from '@utils/utils';

const Dashboard = () => {
  const [selected, setSelected] = useState(new Set<string>());
  const [orders, setOrders] = useState([] as Order[]);

  // const navigate = useNavigate();
  // const { user } = useAuth();
  const { getOrders, updateOrderStatus } = useOrderActions();

  const updateOrders = () => getOrders().then((orders) => setOrders(orders));

  useEffect(() => {
    updateOrders();
  }, []);

  const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    const { name } = e.currentTarget;
    Promise.all(Array.from(selected).map(
      (id) => updateOrderStatus(id, name as Status),
    )).then(updateOrders);

    setSelected(new Set<string>());
  };

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
        <Container direction="column" align="flex-start" style={{ width: '80%' }}>
          <h2>Change Order Status</h2>
          <Container unpadded direction="row" style={{ marginTop: '1rem' }}>
            {Object.values(Status).map((status) => (
              <Button
                outline
                key={status}
                onClick={handleClick}
                name={status}
                variant="transparent"
              >
                {toTitleCase(status)}
              </Button>
            ))}
          </Container>
          <hr />
        </Container>
        <Orders orders={orders} setSelected={setSelected} selected={selected} />
      </>
    </SplitView>
  );
};

export default Dashboard;
