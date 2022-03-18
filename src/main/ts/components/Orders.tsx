import { useState } from 'react';
import Order from 'types/Order';

type OrdersProps = { orders: Order[] };

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
    <table>
      <thead>
        <tr>
          <th><input aria-label="Select All" type="checkbox" checked={allSelected()} onChange={toggleAll} /></th>
          <th>User</th>
          <th>Status</th>
          <th>Order Number</th>
          <th>Price</th>
          <th>Date</th>
        </tr>
      </thead>
      <tbody>
        {orders.map(({
          user, status, id, price, timestamp, number,
        }) => (
          <tr key={id} className={selected.has(id) ? 'selected' : ''}>
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
              {number}
            </td>
            <td>
              €
              {price}
            </td>
            <td>{formatDate(timestamp)}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default Orders;
