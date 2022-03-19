import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTheme } from 'styled-components';

import { useAuthActions } from '@hooks';
import { Container } from '@containers';
import {
  Button, ErrorToast, Form, Input,
} from '@components';
import type { Theme } from '@styles/theme';

const Register = () => {
  const navigate = useNavigate();
  const theme = useTheme() as Theme;
  const { register } = useAuthActions();

  const [state, setState] = useState({
    name: '', email: '', password: '', confirmation: '',
  });

  const [errors, setErrors] = useState({
    name: false, email: false, password: false, confirmation: false,
  });

  const [error, setError] = useState('');

  const [showPassword, setShowPassword] = useState(false);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setState((prevState) => ({ ...prevState, [name]: value }));
    setErrors((prevState) => ({ ...prevState, [name]: false }));
  };

  const {
    name, email, password, confirmation,
  } = state;

  const empty = (value: string) => value.length === 0;
  const validateName = () => !empty(name);
  const validateEmail = () => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  const validatePassword = () => password.length >= 8;
  const validateConfirmation = () => validatePassword() && password === confirmation;

  const handleSubmit = async () => {
    if (!validateConfirmation()) {
      setError('Those passwords didn’t match. Try again.');
      setErrors((prevState) => ({ ...prevState, confirmation: true }));
      setState((prevState) => ({ ...prevState, confirmation: '' }));
    }
    if (!validatePassword()) {
      setError('Use 8 characters or more for your password');
      setErrors((prevState) => ({ ...prevState, password: true }));
    }
    if (!validateEmail()) {
      setError('Enter a valid email.');
      setErrors((prevState) => ({ ...prevState, email: true }));
    }
    if (!validateName()) {
      setError('Enter your name.');
      setErrors((prevState) => ({ ...prevState, name: true }));
    }

    if (validateName() && validateEmail() && validatePassword() && validateConfirmation()) {
      register(name, email, password)
        .then(() => navigate('/marketplace'))
        .catch(setError);
    }
  };

  return (
    <Container fullscreen justify="center" color={theme.colors.primary}>
      <Form onSubmit={handleSubmit}>
        <div>
          <h2>Welcome to Dublin Analytica!</h2>
          <p>By continuing you agree to the terms of service and privacy policy.</p>
        </div>
        <div>
          <Input label="Name" name="name" value={name} onChange={handleChange} valid={validateName()} error={errors.name} />
          <Input label="Email" name="email" value={email} onChange={handleChange} valid={validateEmail()} error={errors.email} />
          <Input label="Password" name="password" value={password} type={showPassword ? 'text' : 'password'} placeholder="8+ characters" onChange={handleChange} valid={validatePassword()} error={errors.password} />
          <Input label="Confirm Password" name="confirmation" value={confirmation} type={showPassword ? 'text' : 'password'} placeholder="8+ characters" onChange={handleChange} valid={validateConfirmation()} error={errors.confirmation} />
          <div style={{ textAlign: 'left', marginLeft: '3.5rem' }}>
            <input
              type="checkbox"
              checked={showPassword}
              onChange={() => setShowPassword(!showPassword)}
            />
            Show password
          </div>
        </div>
        <ErrorToast message={error} />
        <Button type="submit" color={theme.colors.primary}>Register</Button>
        <hr />
        <p>
          Already a member?
          {' '}
          <a href="/login">Sign In</a>
        </p>
      </Form>
    </Container>
  );
};

export default Register;
