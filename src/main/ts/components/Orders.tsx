import { Container } from '@containers';
import React from 'react';
import styled from 'styled-components';
import Order from 'types/Order';
import { toTitleCase, formatDate } from '@utils/utils';
import Table from './Table';

type OrdersProps = {
  orders: Order[],
  setSelected?: React.Dispatch<React.SetStateAction<Set<string>>>
  selected?: Set<string>
};

const S = {
  H1: styled.h1`
    color: ${({ theme }) => theme.text.colors.secondary};
  `,
};

const Orders = ({ orders, setSelected, selected }: OrdersProps) => {
  const formatStatus = toTitleCase;

  const headers = ['User', 'Status', 'Order Number', 'Price', 'Date'];
  const rows = orders.map(({
    id, user, status, number, price, timestamp,
  }) => ({
    id,
    values: [
      user.name,
      formatStatus(status),
      `# ${number.toString().padStart(4, '0')}`,
      `€${price.toFixed(2)}`,
      formatDate(timestamp),
    ],
  }));

  return (
    <Container style={{ marginTop: '1rem' }}>
      {orders.length === 0 && <S.H1>You haven&apos;t made any orders yet!</S.H1>}
      {orders.length > 0
        && <Table setSelected={setSelected} selected={selected} headers={headers} rows={rows} />}
    </Container>
  );
};

export default Orders;
