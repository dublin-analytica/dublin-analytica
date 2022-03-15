import { useNavigate } from 'react-router-dom';
import styled, { useTheme } from 'styled-components';

import { Button } from '@components';
import { Container } from '@containers';

import type { Theme } from '@styles/theme';

import Logo from './Logo';
import Title from './Title';

const S = {
  Navbar: styled.nav`
    /* position: fixed; */
    width: 70%;
    margin-top: ${({ theme }) => theme.spacing.large};
    /* top: 0; */
  `,
};

const Navbar = () => {
  const navigate = useNavigate();
  const { text, colors } = useTheme() as Theme;

  return (
    <S.Navbar>
      <Container unpadded direction="row" color={colors.light} justify="space-between">
        <Container direction="row">
          <Logo fill={text.colors.dark} width={120} height={120} />
          <Title color={text.colors.dark} size="2rem" />
        </Container>
        <Container direction="row" justify="flex-end">
          <Button variant="transparent" onClick={() => navigate('/marketplace')}>Marketplace</Button>
          <Button variant="transparent" onClick={() => navigate('/signin')}>Sign In</Button>
          <Button onClick={() => navigate('/signup')}>Sign Up</Button>
        </Container>
      </Container>
    </S.Navbar>
  );
};

export default Navbar;
