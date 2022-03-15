import { EyeIcon, EyeOffIcon } from '@heroicons/react/solid';
import { Theme } from '@styles/theme';
import { useState } from 'react';
import styled, { useTheme } from 'styled-components';

type InputProps = JSX.IntrinsicElements['input'] & {
  valid?: boolean;
  active?: boolean;
  error?: boolean;
  label?: string;
};

const S = {
  InputContainer: styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100px;
  `,

  Input: styled.input<InputProps>`
    width: 80%;
    height: 50px;
    border-radius: 5px;
    padding: 0 20px;
    font-size: ${({ theme }) => theme.typography.size.default};
    color: ${({ theme }) => theme.text.colors.dark};
    font-size: ${({ theme }) => theme.typography.size.default};
    border: 2px solid
      ${({
    valid, error, active, theme,
  }) => {
    if (valid) return theme.colors.success;
    if (error) return theme.colors.error;
    if (active) return theme.colors.primary;
    return theme.colors.gray;
  }};

    &:focus {
      outline: none;
    }
  `,

  Label: styled.label`
    display: block;
    margin-bottom: ${({ theme }) => theme.spacing.small};
    color: ${({ theme }) => theme.text.colors.dark};
    align-self: start;
    margin-left: 7%;
    font-size: ${({ theme }) => theme.typography.size.small};
    font-weight: ${({ theme }) => theme.typography.weight.bold};
    margin-bottom: 0;
  `,

  EyeButton: styled.i`
    background-color: transparent;
    border: none;
    padding: 0;
    margin: 0;
    cursor: pointer;
    margin-left: ${({ theme }) => theme.spacing.small};
    top: -38px;
    margin-left: auto;
    margin-right: 3rem;
    position: relative;
  `,
};

const Input = ({
  valid = false,
  active = false,
  error = false,
  label = '',
  onChange,
  type = 'text',
  value,
  placeholder,
}: InputProps) => {
  const theme = useTheme() as Theme;

  const [activeType, setActiveType] = useState(type);

  const handleClick = () => {
    setActiveType((current) => (current === 'password' ? 'text' : 'password'));
  };

  return (
    <S.InputContainer>
      <S.Label htmlFor={label}>{label}</S.Label>
      <S.Input
        name={label}
        type={activeType}
        valid={valid}
        active={active}
        error={error}
        onChange={onChange}
        value={value}
        placeholder={placeholder}
      />
      {type === 'password'
      && (
      <S.EyeButton onClick={handleClick}>
        {activeType === 'password'
          ? <EyeIcon width={25} height={25} fill={theme.colors.primary} />
          : <EyeOffIcon width={25} height={25} fill={theme.colors.primary} />}
      </S.EyeButton>
      )}
    </S.InputContainer>
  );
};

export default Input;
