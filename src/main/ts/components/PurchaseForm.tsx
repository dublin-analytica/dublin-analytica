import { useEffect, useState } from 'react';
import { useCartActions } from '@hooks';

import styled from 'styled-components';
import { Container } from '@containers';

import Input from './Input';
import Button from './Button';

type PurchaseFormProps = { id: string, size: number, unitPrice: number };

const S = {
  Form: styled(Container)`
    height: 100%;
  `,

  Divider: styled.div`
  border-top: 1px solid ${({ theme }) => theme.colors.gray};
  margin-top: 1rem;
  margin-bottom: 1rem;
  `,

  Price: styled.span`
    font-size: 1rem;
    color: ${({ theme }) => theme.text.colors.dark};
    text-align: left;
  `,

  Button: styled(Button)`
    height: 36px;
    border-radius: 8px;
  `,

  Input: styled(Input)`
    width: 20%;
    height: 48px;
    border-radius: 8px;
    margin: 0;
  `,
};

const PurchaseForm = ({ id, size, unitPrice }: PurchaseFormProps) => {
  const [datapoints, setDatapoints] = useState(100);
  const [value, setValue] = useState('');

  const { addToCart } = useCartActions();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    const value = Number(input);
    if (input === '' || (Number.isInteger(value) && value > 0 && value <= size)) {
      setValue(e.target.value.trim().replace(/\./g, ''));
    }
  };

  const handleClick = () => {
    addToCart(id, datapoints);
  };

  useEffect(() => {
    if (value === '') setDatapoints(100);
    else setDatapoints(parseInt(value, 10));
  }, [value]);

  return (
    <S.Form unpadded nomargin align="flex-start" justify="flex-end">
      <Container unpadded nomargin direction="row">
        <S.Input placeholder="100" label="Datapoints" value={value} onChange={handleChange} />
      </Container>
      <S.Divider />
      <Container unpadded nomargin direction="row" justify="space-between">
        <S.Price>
          €
          {(unitPrice * datapoints).toFixed(2)}
        </S.Price>
        <S.Button onClick={handleClick}>Add to Basket</S.Button>
      </Container>
    </S.Form>
  );
};

export default PurchaseForm;
