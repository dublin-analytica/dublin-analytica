import styled from 'styled-components';

type ButtonProps = JSX.IntrinsicElements['button'] & { variant?: 'primary' | 'secondary' | 'transparent' };

const S = {
  Button: styled.button<ButtonProps>`
    background-color: ${({ variant = 'primary', theme }) => theme.colors[variant]};
    border: none;
    border-radius: ${({ theme }) => theme.spacing.medium};
    height: 48px;
    color: ${({ variant, theme }) => {
    const { dark, light } = theme.text.colors;
    return variant === 'transparent' ? dark : light;
  }};
    cursor: pointer;
    font-size: ${({ theme }) => theme.typography.size.default};
    font-weight: ${({ theme }) => theme.typography.weight.bold};
    padding: ${({ theme, variant }) => `0 ${variant === 'transparent' ? 0 : theme.spacing.medium}`};
    transition: all 0.2s ease-in-out;

  &:hover {
    color: ${({ variant, theme }) => (variant === 'transparent' ? theme.colors.primary : theme.text.colors.light)};
    filter: brightness(1.1);
  }

  &:active {
    transform: translateY(2px);
  }
  `,
};

const Button = ({
  children, type, variant = 'primary', onClick,
}: ButtonProps) => (
  <S.Button type={type} onClick={onClick} variant={variant}>{children}</S.Button>
);

export default Button;
