import { useNavigate } from 'react-router-dom';
import styled, { useTheme } from 'styled-components';

import { Button } from '@components';
import { Container } from '@containers';

import type { Theme } from '@styles/theme';

import Logo from './Logo';
import Title from './Title';

type NavbarProps = { scrolled: boolean };

const S = {
  Navbar: styled.nav<NavbarProps>`
    position: ${({ scrolled }) => (scrolled ? 'fixed' : 'absolute')};
    top: ${({ scrolled }) => (scrolled ? '-20px' : '64px')};
    width: 70%;
    /* top: 0; */
  `,
};

const Navbar = ({ scrolled }: NavbarProps) => {
  const navigate = useNavigate();
  const { text, colors } = useTheme() as Theme;

  return (
    <S.Navbar scrolled={scrolled}>
      <Container unpadded direction="row" color={colors.white} justify="space-between">
        <Container direction="row">
          <Logo onClick={() => navigate('/')} fill={text.colors.dark} width={120} height={120} />
          <Title onClick={() => navigate('/')} color={text.colors.dark} size="2rem" />
        </Container>
        <Container direction="row" justify="flex-end">
          <Button variant="transparent" onClick={() => navigate('/marketplace')}>Marketplace</Button>
          <Button variant="transparent" onClick={() => navigate('/login')}>Sign In</Button>
          <Button onClick={() => navigate('/signup')}>Sign Up</Button>
        </Container>
      </Container>
    </S.Navbar>
  );
};

export default Navbar;
