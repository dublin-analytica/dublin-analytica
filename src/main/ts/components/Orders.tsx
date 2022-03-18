import { useEffect, useState } from 'react';

import Order from 'types/Order';
import { useOrderActions } from '@hooks';

const Orders = () => {
  const [orders, setOrders] = useState([] as Order[]);
  const [selected, setSelected] = useState(new Set() as Set<number>);

  const { getOrders } = useOrderActions();

  useEffect(() => {
    getOrders().then(setOrders);
    return () => setOrders([]);
  }, []);

  const toggleSelected = (id: number) => () => {
    if (selected.has(id)) selected.delete(id);
    else selected.add(id);
    setSelected(selected);
  };

  const toggleAll = () => {
    if (selected.size === orders.length) selected.clear();
    else orders.forEach(({ id }) => selected.add(id));

    setSelected(selected);
  };

  const allSelected = selected.size === orders.length;

  const formatStatus = (status: string) => status.slice(0, 1).toUpperCase() + status.slice(1);

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
          <th><input aria-label="Select All" type="checkbox" checked={allSelected} onChange={toggleAll} /></th>
          <th>User</th>
          <th>Status</th>
          <th>Order Number</th>
          <th>Price</th>
          <th>Date</th>
        </tr>
      </thead>
      <tbody>
        {orders.map(({
          user, status, id, price, timestamp,
        }) => (
          <tr key={id} className={selected.has(id) ? 'selected' : ''}>
            <td><input aria-label="Select" type="checkbox" checked={selected.has(id)} onChange={toggleSelected(id)} /></td>
            <td>{user.name}</td>
            <td>{formatStatus(status)}</td>
            <td>
              #
              {id}
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
