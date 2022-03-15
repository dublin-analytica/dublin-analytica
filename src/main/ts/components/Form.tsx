import { Container } from '@containers';
import { Theme } from '@styles/theme';
import styled, { useTheme } from 'styled-components';

type FormProps = JSX.IntrinsicElements['form'] & { children: React.ReactNode };

const S = {
  Form: styled.form`
    width: 35%;
    height: 70vh;

    & h2 {
      margin-bottom: 1rem;
      font-weight: ${({ theme }) => theme.typography.weight.bold};
      font-size: ${({ theme }) => theme.typography.size.large};
      color: ${({ theme }) => theme.text.colors.dark};
    }

    & span {
      color: ${({ theme }) => theme.colors.dark};
    }

    & ${Container} {
      height: 100%;
    }

    & div {
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 100%;
    }

    & div span a {
      color: ${({ theme }) => theme.text.colors.primary} !important;
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
      <Container color={theme.colors.white} justify="space-around">
        {children}
      </Container>
    </S.Form>
  );
};

export default Form;
