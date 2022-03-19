import { Container } from '@containers';
import styled from 'styled-components';
import Order from 'types/Order';
import Table from './Table';

type OrdersProps = { orders: Order[] };

const S = {
  H1: styled.h1`
    color: ${({ theme }) => theme.text.colors.secondary};
  `,
};

const Orders = ({ orders }: OrdersProps) => {
  const formatStatus = (status: string) => status.slice(0, 1) + status.slice(1).toLowerCase();

  const formatDate = (timestamp: number) => {
    const getArgs = (diff: number): [number, Intl.RelativeTimeFormatUnit] => {
      const seconds = Math.floor(diff / 1000);

      if (seconds < 60) return [seconds, 'second'];
      if (seconds < 3600) return [seconds / 60, 'minute'];
      if (seconds < 86400) return [seconds / 3600, 'hour'];
      if (seconds < 604800) return [seconds / 86400, 'day'];
      if (seconds < 2419200) return [seconds / 604800, 'week'];
      if (seconds < 29030400) return [seconds / 2419200, 'month'];
      return [seconds / 2419200, 'year'];
    };

    const formatter = new Intl.RelativeTimeFormat('en', { style: 'narrow' });
    const date = new Date(timestamp);
    const diff = Math.round((new Date().getTime() - date.getTime()));

    const [magnitude, unit] = getArgs(diff);
    return formatter.format(-Math.round(magnitude), unit);
  };

  const headers = ['User', 'Status', 'Order Number', 'Price', 'Date'];
  const rows = orders.map(({
    id, user, status, number, price, timestamp,
  }) => ({
    id,
    values: [
      user.name,
      formatStatus(status),
      `# ${number.toString().padStart(4, '0')}`,
      `€${price}`,
      formatDate(timestamp),
    ],
  }));

  return (
    <Container style={{ marginTop: '4rem' }}>
      {orders.length === 0 && <S.H1>You haven&apos;t made any orders yet!</S.H1>}
      {orders.length > 0 && <Table headers={headers} rows={rows} />}
    </Container>
  );
};

export default Orders;
