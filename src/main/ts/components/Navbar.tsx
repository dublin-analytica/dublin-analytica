import { useNavigate } from 'react-router-dom';
import styled, { useTheme } from 'styled-components';

import { Button } from '@components';
import { Container } from '@containers';

import type { Theme } from '@styles/theme';

import { useAuth } from '@context/AuthProvider';
import Logo from './Logo';
import Title from './Title';

type NavbarProps = { scrolled: boolean };

type NavLinkProps = { text: string; to: string; primary?: boolean; };

const S = {
  Navbar: styled.nav<NavbarProps>`
    position: ${({ scrolled }) => (scrolled ? 'fixed' : 'absolute')};
    top: ${({ scrolled }) => (scrolled ? '-20px' : '64px')};
    width: 90%;
    display: flex;
    justify-content: center;
  `,
};

const NavLink = ({ text, to, primary }: NavLinkProps) => {
  const navigate = useNavigate();
  return (
    <Button
      variant={primary ? 'primary' : 'transparent'}
      onClick={() => navigate(to)}
    >
      {text}
    </Button>
  );
};

const Navbar = ({ scrolled }: NavbarProps) => {
  const navigate = useNavigate();
  const { text, colors } = useTheme() as Theme;
  const { user } = useAuth();

  return (
    <S.Navbar scrolled={scrolled}>
      <Container unpadded direction="row" color={colors.white} justify="space-between">
        <Container direction="row">
          <Logo onClick={() => navigate('/')} fill={text.colors.dark} width={120} height={120} />
          <Title onClick={() => navigate('/')} color={text.colors.dark} size="2rem" />
        </Container>
        <Container direction="row" justify="flex-end">
          <NavLink text="Marketplace" to="/marketplace" />
          {user && <NavLink text="Basket" to="/basket" />}
          {!user && <NavLink text="Login" to="/login" />}
          {user && !user?.admin && <NavLink text="My Account" to="/account" primary />}
          {user?.admin && <NavLink text="Dashboard" to="/dashboard" primary />}
          {!user && <NavLink text="Sign Up" to="/register" primary />}
        </Container>
      </Container>
    </S.Navbar>
  );
};

export default Navbar;
