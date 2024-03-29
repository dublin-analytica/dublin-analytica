import User from './User';
import Status from './Status';

type Order = {
  id: string;
  number: number;
  status: Status;
  user: User;
  timestamp: number;
  price: number;
  items: Record<string, number>;
}

export default Order;
