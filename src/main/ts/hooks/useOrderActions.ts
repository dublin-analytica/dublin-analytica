import Status from 'types/Status';
import Order from 'types/Order';

import useAPI from './useAPI';

const useOrderActions = () => {
  const { get, post } = useAPI();

  const getOrders = async (user?: string): Promise<Order[]> => {
    const orders: Order[] = await get(user ? `orders/user/${user}` : 'orders');
    return orders.map((order) => ({ ...order, timestamp: order.timestamp * 1000 }))
      .sort((a, b) => {
        if (a.status === Status.DELIVERED && b.status !== Status.DELIVERED) return -1;
        return b.timestamp - a.timestamp;
      });
  };

  const getOrder = (id: string): Promise<Order> => get(`orders/${id}`);

  const updateOrderStatus = (id: string, status: Status) => (
    post(`orders/${id}/status`, { status })
  );

  return { getOrders, getOrder, updateOrderStatus };
};

export default useOrderActions;
