import { useTheme } from 'styled-components';

import { Button, Form, Input } from '@components';
import { Container } from '@containers';
import type { Theme } from '@styles/theme';
import { useState } from 'react';
import { useAuthActions } from '@hooks';

const Login = () => {
  const theme = useTheme() as Theme;
  const { login } = useAuthActions();

  const [state, setState] = useState({
    name: '', email: '', password: '', confirmation: '',
  });

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setState((prevState) => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = () => {
    const { name, email } = state;
    login(name, email)
      .then(console.log)
      .catch(console.error);
  };

  return (
    <div>
      <Container nav color={theme.colors.primary}>
        <Form onSubmit={handleSubmit}>
          <div>
            <h2>Welcome back!</h2>
            <span>Please sign in to your account</span>
          </div>
          <div>
            <Input label="Email" name="email" onChange={handleChange} />
            <Input label="Password" name="password" type="password" placeholder="8+ characters" onChange={handleChange} />
          </div>
          <Button type="submit" color={theme.colors.primary}>Sign In</Button>
          <span>
            Not a member?
            {' '}
            <a href="/register">Sign Up</a>
          </span>
        </Form>
      </Container>
    </div>
  );
};

export default Login;
