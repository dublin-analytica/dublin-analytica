import User from './User';
import Status from './Status';

type Order = {
  id: number;
  status: Status;
  user: User;
  timestamp: number;
  price: number;
  items: { [key: string]: number };
}

export default Order;
