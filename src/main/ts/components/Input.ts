import styled from 'styled-components';

type InputProps = { valid?: boolean; focused?: boolean; error?: boolean };

const Input = styled.input<InputProps>`
  width: 80%;
  height: 50px;
  border-radius: 5px;
  padding: 0 20px;
  border: none;
  border-bottom: 3px solid ${({
    valid, error, focused, theme,
  }) => {
    if (valid) return theme.colors.success;
    if (error) return theme.colors.error;
    if (focused) return theme.colors.info;
    return theme.colors.transparent;
  }};
`;

export default Input;
