import Status from 'types/Status';
import Order from 'types/Order';

import useAPI from './useAPI';

const useOrderActions = () => {
  const { get, post } = useAPI();

  const getOrders = (): Promise<Order[]> => get('orders');

  const getOrder = (id: number): Promise<Order> => get(`orders/${id}`);

  const updateOrderStatus = (id: number, status: Status) => (
    post(`orders/${id}/status`, { status })
  );

  return { getOrders, getOrder, updateOrderStatus };
};

export default useOrderActions;
