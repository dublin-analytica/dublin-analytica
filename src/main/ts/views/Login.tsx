import { useTheme } from 'styled-components';

import { SmartForm as Form } from '@components';
import { Container } from '@containers';
import type { Theme } from '@styles/theme';
import { useAuthActions } from '@hooks';
import { useNavigate } from 'react-router-dom';
import { SubmitAction } from '@components/SmartForm';

const Login = () => {
  const navigate = useNavigate();
  const theme = useTheme() as Theme;
  const { login } = useAuthActions();

  const fields = [
    { name: 'email', label: 'Email' },
    { name: 'password', label: 'Password', type: 'password' },
  ];

  const handleSubmit: SubmitAction = ({ email, password }, setError) => {
    login(email!, password!)
      .then((user) => navigate(user?.admin ? '/dashboard' : '/marketplace'))
      .catch(setError);
  };

  return (
    <Container color={theme.colors.primary} justify="center" fullscreen>
      <Form onSubmit={handleSubmit} fields={fields} action="Sign In">
        <div>
          <h2>Welcome back!</h2>
          <p>Please sign in to your account</p>
        </div>
        <p>
          Not a member?
          {' '}
          <a href="/register">Sign Up</a>
        </p>
      </Form>
    </Container>
  );
};

export default Login;
