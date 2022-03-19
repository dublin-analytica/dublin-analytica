import { useNavigate } from 'react-router-dom';
import { useTheme } from 'styled-components';

import { useAuthActions } from '@hooks';
import { Container } from '@containers';
import { SmartForm as Form } from '@components';
import type { Theme } from '@styles/theme';

import {
  nameValidator, emailValidator, passwordValidator, confirmationValidator,
} from '@utils/validators';
import { SubmitAction } from '@components/SmartForm';

const Register = () => {
  const navigate = useNavigate();
  const theme = useTheme() as Theme;
  const { register } = useAuthActions();

  const fields = [
    { name: 'name', label: 'Name', validator: nameValidator },
    {
      name: 'email', label: 'Email', validator: emailValidator,
    },
    {
      name: 'password', label: 'Password', type: 'password', validator: passwordValidator,
    },
    {
      name: 'confirmation', label: 'Confirm Password', type: 'password', validator: confirmationValidator,
    },
  ];

  const handleSubmit: SubmitAction = async ({ name, email, password }, setError) => {
    register(name!, email!, password!)
      .then(() => navigate('/marketplace'))
      .catch(setError);
  };

  return (
    <Container fullscreen justify="center" color={theme.colors.primary}>
      <Form onSubmit={handleSubmit} fields={fields} action="Sign Up" validated>
        <div>
          <h2>Welcome to Dublin Analytica!</h2>
          <p>By continuing you agree to the terms of service and privacy policy.</p>
        </div>
        <div>
          <hr />
          <p>
            Already a member?
            {' '}
            <a href="/login">Sign In</a>
          </p>
        </div>
      </Form>
    </Container>
  );
};

export default Register;
