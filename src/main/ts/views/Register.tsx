import { useTheme } from 'styled-components';

import { Button, Form, Input } from '@components';
import { Container } from '@containers';
import type { Theme } from '@styles/theme';
import { useState } from 'react';
import { useAuthActions } from '@hooks';

const Register = () => {
  const theme = useTheme() as Theme;
  const { register } = useAuthActions();

  const [state, setState] = useState({
    name: '', email: '', password: '', confirmation: '',
  });

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setState((prevState) => ({ ...prevState, [name]: value }));
  };

  const {
    name, email, password, confirmation,
  } = state;

  const empty = (value: string) => value.length === 0;
  const validateName = () => !empty(name);
  const validateEmail = () => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  const validatePassword = () => password.length > 8;
  const validateConfirmation = () => password === confirmation;

  const handleSubmit = () => {
    if (validateName() && validateEmail() && validatePassword() && validateConfirmation()) {
      register(name, email, password)
        .then(console.log)
        .catch(console.error);
    } else {
      alert('Invalid form');
    }
  };

  return (
    <div>
      <Container nav color={theme.colors.primary}>
        <Form onSubmit={handleSubmit}>
          <div>
            <h2>Welcome to Dublin Analytica!</h2>
            <span>By continuing you agree to the terms of service and privacy policy.</span>
          </div>
          <div>
            <Input label="Name" name="name" onChange={handleChange} valid={validateName()} />
            <Input label="Email" name="email" onChange={handleChange} valid={validateEmail()} />
            <Input label="Password" name="password" type="password" placeholder="8+ characters" onChange={handleChange} valid={validatePassword()} />
            <Input label="Confirm Password" name="confirmation" type="password" placeholder="8+ characters" onChange={handleChange} valid={validateConfirmation()} />
          </div>
          <Button type="submit" color={theme.colors.primary}>Register</Button>
          <span>
            Already a member?
            {' '}
            <a href="/login">Sign In</a>
          </span>
        </Form>
      </Container>
    </div>
  );
};

export default Register;
