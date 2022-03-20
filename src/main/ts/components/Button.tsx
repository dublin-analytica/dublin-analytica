import styled from 'styled-components';

type ButtonProps = JSX.IntrinsicElements['button'] & { variant?: 'primary' | 'secondary' | 'transparent', unpadded?: boolean, outline?: boolean };

const S = {
  Button: styled.button<ButtonProps>`
    background-color: ${({ variant = 'primary', theme }) => theme.colors[variant]};
    border: ${({ outline, theme }) => (outline ? `1px solid ${theme.text.colors.dark}` : 'none')};
    border-radius: ${({ theme }) => theme.spacing.medium};
    height: 48px;
    color: ${({ variant, theme }) => {
    const { dark, light } = theme.text.colors;
    return variant === 'transparent' ? dark : light;
  }};
    cursor: pointer;
    font-size: ${({ theme }) => theme.typography.size.default};
    font-weight: ${({ theme }) => theme.typography.weight.bold};
    padding: ${({ theme, unpadded }) => `0 ${unpadded ? 0 : theme.spacing.medium}`};
    transition: all 0.2s ease-in-out;

  &:hover {
    color: ${({ variant, theme }) => (variant === 'transparent' ? theme.colors.primary : theme.text.colors.light)};
    filter: brightness(1.1);
  }

  &:active {
    transform: translateY(2px);
  }

  &:disabled {
    background-color: ${({ theme }) => theme.colors.gray};
    color: ${({ theme }) => theme.text.colors.light};
    cursor: default;
    border: none;
  }

  &:disabled:hover {
    filter: brightness(1);
  }
  `,
};

const Button = ({
  children,
  style,
  outline = false,
  name,
  type,
  variant = 'primary',
  unpadded = false,
  onClick,
  className,
  disabled,
}: ButtonProps) => (
  <S.Button
    style={style}
    className={className}
    outline={outline}
    name={name}
    unpadded={unpadded}
    type={type}
    onClick={onClick}
    variant={variant}
    disabled={disabled}
  >
    {children}
  </S.Button>
);

export default Button;
