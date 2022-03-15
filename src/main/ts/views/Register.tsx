import { useTheme } from 'styled-components';

import { Form, Input } from '@components';
import { Container } from '@containers';
import type { Theme } from '@styles/theme';

const Register = () => {
  const theme = useTheme() as Theme;

  return (
    <div>
      <Container nav color={theme.colors.primary}>
        <Form>
          <h2>Welcome to Dublin Analytica</h2>
          <Input label="Email" />
          <Input label="Password" type="password" placeholder="8+ characters" />
          <Input label="Confirm Password" type="password" placeholder="8+ characters" />
        </Form>
      </Container>
    </div>
  );
};

export default Register;
