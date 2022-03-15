import { useTheme } from 'styled-components';

import { Input } from '@components';
import { Container } from '@containers';
import type { Theme } from '@styles/theme';

const Signup = () => {
  const theme = useTheme() as Theme;

  return (
    <div>
      <Container nav color={theme.colors.primary}>
        <h1>Signup</h1>
        <Input valid />
      </Container>
    </div>
  );
};

export default Signup;
