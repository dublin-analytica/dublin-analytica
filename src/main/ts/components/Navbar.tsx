import { useNavigate } from 'react-router-dom';
import styled, { useTheme } from 'styled-components';

import { Container } from '@containers';
import type { Theme } from '@styles/theme';

import { useAuth } from '@context/AuthProvider';
import Logo from './Logo';
import Title from './Title';
import Link from './Link';

type NavbarProps = { scrolled: boolean };

const S = {
  Navbar: styled.nav<NavbarProps>`
    position: ${({ scrolled }) => (scrolled ? 'fixed' : 'absolute')};
    top: ${({ scrolled }) => (scrolled ? '-20px' : '64px')};
    width: 90%;
    display: flex;
    justify-content: center;
    z-index: 1;

    & > ${Container} {
      border: 1px solid ${({ theme }) => theme.colors.gray};
    }
  `,
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
          <Link text="Marketplace" to="/marketplace" unpadded />
          {user && <Link text="Basket" to="/basket" unpadded />}
          {!user && <Link text="Login" to="/login" unpadded />}
          {user && !user?.admin && <Link text="My Account" to="/account" primary />}
          {user?.admin && <Link text="Dashboard" to="/orderdashboard" primary />}
          {!user && <Link text="Sign Up" to="/register" primary />}
        </Container>
      </Container>
    </S.Navbar>
  );
};

export default Navbar;
