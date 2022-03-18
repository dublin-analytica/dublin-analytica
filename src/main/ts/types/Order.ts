import User from './User';
import Status from './Status';

type Order = {
  id: string;
  status: Status;
  user: User;
  items: { [key: string]: number };
}

export default Order;
