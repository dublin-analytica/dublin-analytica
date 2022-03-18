import { Button } from '@components';
import { Container } from '@containers';
import { useAuthActions } from '@hooks';

const Account = () => {
  const { logout } = useAuthActions();

  return (
    <div>
      <Container nav>
        <h1>Account</h1>
        <Button onClick={logout}>Sign Out</Button>
      </Container>
    </div>
  );
};

export default Account;
