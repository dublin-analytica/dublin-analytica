import { Container } from '@containers';
import { Theme } from '@styles/theme';
import styled, { useTheme } from 'styled-components';

type FormProps = JSX.IntrinsicElements['form'] & { children: React.ReactNode };

const S = {
  Form: styled.form`
    width: 35%;

    & h2 {
      margin-bottom: 1rem;
      font-weight: ${({ theme }) => theme.typography.weight.bold};
      color: ${({ theme }) => theme.text.colors.dark};
    }
  `,
};

const Form = ({ children, onSubmit }: FormProps) => {
  const theme = useTheme() as Theme;

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (onSubmit) onSubmit(e);
  };

  return (
    <S.Form onSubmit={handleSubmit}>
      <Container color={theme.colors.white}>
        {children}
      </Container>
    </S.Form>
  );
};

export default Form;
