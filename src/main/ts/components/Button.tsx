import styled from 'styled-components';

type ButtonProps = JSX.IntrinsicElements['button'] & { variant?: 'primary' | 'secondary' | 'transparent' };

const S = {
  Button: styled.button<ButtonProps>`
    background-color: ${({ variant = 'primary', theme }) => theme.colors[variant]};
    border: none;
    border-radius: 16px;
    height: 48px;
    color: ${({ variant, theme }) => {
    const { dark, light } = theme.colors.text;
    return variant === 'transparent' ? dark : light;
  }};
    cursor: pointer;
    font-size: 1rem;
    font-weight: 500;
    padding: 0rem 16px;
    transition: background-color 0.2s ease-in-out;
  `,
};

const Button = ({ children, variant = 'primary', onClick }: ButtonProps) => (
  <S.Button onClick={onClick} variant={variant}>{children}</S.Button>
);

export default Button;
