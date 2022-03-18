import React from 'react';
import styled from 'styled-components';

type CheckboxProps = { checked: boolean; onChange: React.ChangeEventHandler<HTMLInputElement> };

const S = {
  Checkbox: styled.input`
    appearance: none;
    border: 1px solid #34495E;
    border-radius: 4px;
    outline: none;
    background-color: #41B883;
    cursor: pointer;
  `,
};

const Checkbox = ({ checked, onChange }: CheckboxProps) => (
  <S.Checkbox
    type="checkbox"
    checked={checked}
    onChange={onChange}
  />
);

export default Checkbox;
