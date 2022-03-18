import { useNavigate } from 'react-router-dom';

import { useAuth } from '@context/AuthProvider';
import { Orders, SplitView } from '@components';

const Dashboard = () => {
  const navigate = useNavigate();
  const { user } = useAuth();

  if (!user?.admin) navigate('/404');

  return (
    <SplitView>
      <h1>Dashboard</h1>
      <Orders />
    </SplitView>
  );
};

export default Dashboard;
