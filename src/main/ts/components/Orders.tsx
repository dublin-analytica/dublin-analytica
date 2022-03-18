import { useState } from 'react';
import styled from 'styled-components';
import Order from 'types/Order';

type OrdersProps = { orders: Order[] };

const S = {
  Table: styled.table`
    border-collapse: collapse;
    width: 100%;
  `,

  THead: styled.thead`
    border-bottom: 1px solid ${({ theme }) => theme.colors.gray};
  `,

  TH: styled.th`
    text-align: left;
  `,

  TR: styled.tr`
    height: 3rem;
  `,
};

const Orders = ({ orders }: OrdersProps) => {
  const [selected, setSelected] = useState(new Set() as Set<string>);

  const toggleSelected = (id: string) => () => {
    setSelected((prevSelected) => {
      const newSelected = new Set(prevSelected);
      if (newSelected.has(id)) newSelected.delete(id);
      else newSelected.add(id);
      return newSelected;
    });
  };

  const toggleAll = () => {
    setSelected((prevSelected) => {
      const newSelected = new Set(prevSelected);
      if (newSelected.size === orders.length) newSelected.clear();
      else orders.forEach(({ id }) => newSelected.add(id));
      return newSelected;
    });
  };

  const allSelected = () => selected.size === orders.length;

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

  return (
    <S.Table>
      <S.THead>
        <S.TR>
          <S.TH><input aria-label="Select All" type="checkbox" checked={allSelected()} onChange={toggleAll} /></S.TH>
          <S.TH>User</S.TH>
          <S.TH>Status</S.TH>
          <S.TH>Order Number</S.TH>
          <S.TH>Price</S.TH>
          <S.TH>Date</S.TH>
        </S.TR>
      </S.THead>
      <tbody>
        {orders.map(({
          user, status, id, price, timestamp, number,
        }) => (
          <S.TR key={id} className={selected.has(id) ? 'selected' : ''}>
            <td>
              <input
                aria-label="Select"
                type="checkbox"
                checked={selected.has(id)}
                onChange={toggleSelected(id)}
              />
            </td>
            <td>{user.name}</td>
            <td>{formatStatus(status)}</td>
            <td>
              #
              {' '}
              {number.toString().padStart(4, '0')}
            </td>
            <td>
              €
              {price}
            </td>
            <td>{formatDate(timestamp)}</td>
          </S.TR>
        ))}
      </tbody>
    </S.Table>
  );
};

export default Orders;
