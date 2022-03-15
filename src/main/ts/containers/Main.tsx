import { Navbar } from '@components';
import theme from '@styles/theme';

import Container from './Container';
import Router from './Router';

const Main = () => (

  <Container color={theme.colors.primary}>
    <Navbar />
    <Router />
  </Container>
);

export default Main;
