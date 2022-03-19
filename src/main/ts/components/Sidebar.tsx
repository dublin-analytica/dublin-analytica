import { Container } from '@containers';
import { useAuth } from '@context/AuthProvider';
import { useAuthActions } from '@hooks';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';
import Button from './Button';
import Link from './Link';

const S = {
  Container: styled(Container)`
    text-align: left;
    margin-top: 3rem;
    height: 100%;
    min-height: 40vh;
  `,
};

const Sidebar = () => {
  const { pathname } = useLocation();
  const { user } = useAuth();
  const { logout } = useAuthActions();

  return (
    <S.Container align="flex-start">
      {user?.admin && <Link to="/dashboard" text="Dashboard" primary={pathname === '/dashboard'} />}
      <Link to="/account" text="My Account" primary={pathname === '/account'} />
      <Link to="/orders" text="My Orders" primary={pathname === '/orders'} />
      <div style={{ justifySelf: 'flex-end', marginTop: 'auto' }}>
        <Button onClick={logout} variant="transparent">
          Sign Out
        </Button>
      </div>
    </S.Container>
  );
};

export default Sidebar;
