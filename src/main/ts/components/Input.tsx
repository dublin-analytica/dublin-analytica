import styled from 'styled-components';

type InputProps = JSX.IntrinsicElements['input'] & {
  valid?: boolean;
  active?: boolean;
  error?: boolean;
  label?: string;
};

const S = {
  InputContainer: styled.div`
    width: 100%;
    height: 82px;
  `,

  Input: styled.input<InputProps>`
    width: 80%;
    height: 32px;
    border-radius: 5px;
    padding: 0 5px;
    font-size: ${({ theme }) => theme.typography.size.default};
    color: ${({ theme }) => theme.text.colors.dark};
    font-size: ${({ theme }) => theme.typography.size.default};
    border: 2px solid
      ${({
    valid, error, theme,
  }) => {
    if (error) return theme.colors.error;
    if (valid) return theme.colors.primary;
    return theme.colors.gray;
  }};

    &:focus {
      outline: none;
    }
  `,

  Label: styled.label`
    width: 80%;
    display: inline-block;
    color: ${({ theme }) => theme.text.colors.dark};
    text-align: left;
    font-size: ${({ theme }) => theme.typography.size.small};
    font-weight: ${({ theme }) => theme.typography.weight.bold};
    margin: 0;
    margin-right: 5px;
  `,

  EyeButton: styled.i`
    cursor: pointer;
    margin-left: -30px;
  `,
};

const Input = ({
  valid = false,
  active = false,
  error = false,
  label = '',
  type,
  onChange,
  value,
  placeholder,
  name,
}: InputProps) => (
  <S.InputContainer>
    <S.Label htmlFor={name}>{label}</S.Label>
    <S.Input
      name={name}
      type={type}
      valid={valid}
      active={active}
      error={error}
      onChange={onChange}
      value={value}
      placeholder={placeholder}
    />
  </S.InputContainer>
);

export default Input;
