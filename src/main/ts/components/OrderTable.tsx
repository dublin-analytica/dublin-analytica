import { Container } from '@containers';
import React from 'react';
import styled from 'styled-components';
import Order from 'types/Order';
import { toTitleCase, formatDate } from '@utils/utils';
import { useAPI } from '@hooks';
import Table from './Table';

type OrderTableProps = {
  orders: Order[],
  setSelected?: React.Dispatch<React.SetStateAction<Set<string>>>
  selected?: Set<string>
};

const S = {
  H1: styled.h1`
    color: ${({ theme }) => theme.text.colors.secondary};
  `,
};

const OrderTable = ({ orders, setSelected, selected }: OrderTableProps) => {
  const { get } = useAPI();

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

  const download = (id: string) => {
    get(`orders/${id}/download`);
  };

  return (
    <Container style={{ marginTop: '1rem' }}>
      {orders.length === 0 && <S.H1>You haven&apos;t made any orders yet!</S.H1>}
      {orders.length > 0 && (
        <Table
          setSelected={setSelected}
          selected={selected}
          headers={headers}
          rows={rows}
          action={download}
          actionName="Download"
        />
      )}
    </Container>
  );
};

export default OrderTable;
