import { Button, Sidebar, SplitView } from '@components';
import { useAuthActions } from '@hooks';

const Account = () => {
  const { logout } = useAuthActions();

  return (
    <SplitView>
      <Sidebar />
      <div>
        <Button onClick={logout}>Logout</Button>
      </div>
    </SplitView>
  );
};

export default Account;
