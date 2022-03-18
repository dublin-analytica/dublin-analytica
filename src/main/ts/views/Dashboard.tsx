import { useNavigate } from 'react-router-dom';

import { useAuth } from '@context/AuthProvider';
import { SplitView } from '@components';

const Dashboard = () => {
  const navigate = useNavigate();
  const { user } = useAuth();

  if (!user?.isAdmin && false) navigate('/404');

  return (
    <SplitView>
      <h1>Dashboard</h1>
      <h1>Admin</h1>
    </SplitView>
  );
};

export default Dashboard;
