import { useTheme } from 'styled-components';

import {
  Button, ErrorToast, Form, Input,
} from '@components';
import { Container } from '@containers';
import type { Theme } from '@styles/theme';
import { useState } from 'react';
import { useAuthActions } from '@hooks';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate();
  const theme = useTheme() as Theme;
  const { login } = useAuthActions();

  const [state, setState] = useState({
    email: '', password: '',
  });

  const [error, setError] = useState('');

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setState((prevState) => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = () => {
    const { email, password } = state;
    login(email, password)
      .then(() => navigate('/marketplace'))
      .catch(setError);
  };

  return (
    <div>
      <Container nav color={theme.colors.primary}>
        <Form onSubmit={handleSubmit}>
          <div>
            <h2>Welcome back!</h2>
            <p>Please sign in to your account</p>
          </div>
          <div>
            <Input label="Email" name="email" onChange={handleChange} />
            <Input label="Password" name="password" type="password" onChange={handleChange} />
          </div>
          <ErrorToast message={error} />
          <Button type="submit" color={theme.colors.primary}>Sign In</Button>
          <p>
            Not a member?
            {' '}
            <a href="/register">Sign Up</a>
          </p>
        </Form>
      </Container>
    </div>
  );
};

export default Login;
