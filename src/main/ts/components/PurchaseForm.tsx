import { useEffect, useState } from 'react';
import { useCartActions } from '@hooks';

import styled from 'styled-components';
import { Container } from '@containers';

import Dataset from 'types/Dataset';
import Input from './Input';
import Button from './Button';
import Form from './Form';

type PurchaseFormProps = JSX.IntrinsicElements['form'] & Partial<Dataset> & { variant: 'large' | 'small' };

const S = {
  Form: styled(Form)`
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

  BigPrice: styled.span`
    font-size: 2.5rem;
    font-weight: bold;
    color: ${({ theme }) => theme.colors.primary};
    text-align: left;
    align-self: flex-start;
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

  Title: styled.h1`
    font-size: 3rem;
    color: ${({ theme }) => theme.text.colors.dark};
    text-align: left;
    margin: 0;
    align-self: flex-start;
  `,

  Description: styled.p`
    font-size: 1rem;
    font-weight: bold;
    color: ${({ theme }) => theme.text.colors.secondary};
    text-align: left;
    margin: 0;
    align-self: flex-start;
  `,
};

const PurchaseForm = ({
  id, size, unitPrice, className, variant, name, description,
}: PurchaseFormProps) => {
  const [datapoints, setDatapoints] = useState(100);
  const [value, setValue] = useState('');

  const { addToCart } = useCartActions();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    const value = Number(input);
    if (input === '' || (Number.isInteger(value) && value > 0 && value <= size!)) {
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

  return variant === 'large' ? (
    <Container direction="column" justify="space-between" className={className}>
      <Container>
        <S.Title>
          {name}
        </S.Title>
        <S.BigPrice>
          €
          {unitPrice}
        </S.BigPrice>
        <S.Description>
          {description}
        </S.Description>
        <S.Description>
          {size}
          {' '}
          Datapoints Available
        </S.Description>
      </Container>
      <Container align="flex-start">
        <Container unpadded style={{ paddingBottom: '1.5rem' }} align="flex-start">
          <S.Input placeholder="100" label="Datapoints" value={value} onChange={handleChange} />
        </Container>
        <Button onClick={handleClick}>Add to Basket</Button>
      </Container>
    </Container>
  ) : (
    <S.Form className={className}>
      <Container unpadded nomargin direction="row">
        <S.Input placeholder="100" label="Datapoints" value={value} onChange={handleChange} />
      </Container>
      <S.Divider />
      <Container unpadded nomargin direction="row" justify="space-between">
        <S.Price>
          €
          {(unitPrice! * datapoints).toFixed(2)}
        </S.Price>
        <S.Button onClick={handleClick}>Add to Basket</S.Button>
      </Container>
    </S.Form>
  );
};

export default PurchaseForm;
