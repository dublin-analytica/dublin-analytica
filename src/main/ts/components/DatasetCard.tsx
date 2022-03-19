import { Container } from '@containers';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import type Dataset from 'types/Dataset';
import Button from './Button';
import Input from './Input';

type DatasetCardProps = Dataset

const S = {
  Container: styled(Container)`
    border: 1px solid ${({ theme }) => theme.colors.gray};
    width: 300px;
    cursor: pointer;
  `,

  Body: styled(Container)`
    padding: 1rem;
    margin: 0;

    & > * {
      width: 100%;
    }
  `,

  Img: styled.img`
    width: 300px;
    height: 300px;
    border-top-left-radius: 16px;
    border-top-right-radius: 16px;
    margin-bottom: 0.3rem;
  `,

  Title: styled.h3`
    font-size: 1.5rem !important;
    color: ${({ theme }) => theme.text.colors.dark};
    margin-bottom: 0.5rem;
    text-align: left;
  `,

  Description: styled.p`
    font-size: 1rem;
    color: ${({ theme }) => theme.text.colors.secondary};
    text-align: left;
  `,

  Size: styled.span`
    font-size: 0.8rem;
    color: ${({ theme }) => theme.text.colors.secondary};
    text-align: left;
  `,

  Price: styled.span`
    font-size: 1rem;
    color: ${({ theme }) => theme.text.colors.dark};
    text-align: left;
  `,

  Divider: styled.div`
    border-top: 1px solid ${({ theme }) => theme.colors.gray};
    margin-top: 1rem;
    margin-bottom: 1rem;
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

const DatasetCard = ({
  name, image, description, size, unitPrice,
}: DatasetCardProps) => {
  const [datapoints, setDatapoints] = useState(100);
  const [value, setValue] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    const value = Number(input);
    if (input === '' || (Number.isInteger(value) && value > 0 && value <= size)) {
      setValue(e.target.value.trim().replace(/\./g, ''));
    }
  };

  useEffect(() => {
    if (value === '') setDatapoints(100);
    else setDatapoints(parseInt(value, 10));
  }, [value]);

  return (
    <S.Container nomargin unpadded>
      <S.Img src={image} alt={name} />
      <S.Body align="flex-start">
        <S.Title>{name}</S.Title>
        <S.Description>{description}</S.Description>
        <S.Size>
          {size}
          {' '}
          Datapoints Available
          {' '}
          <br />
          (€
          {unitPrice}
          {' '}
          / datapoint)
        </S.Size>
        <Container unpadded nomargin direction="row">
          <S.Input placeholder="100" label="Datapoints" value={value} onChange={handleChange} />
        </Container>
        <S.Divider />
        <Container unpadded nomargin direction="row" justify="space-between">
          <S.Price>
            €
            {(unitPrice * datapoints).toFixed(2)}
          </S.Price>
          <S.Button>Add to Basket</S.Button>
        </Container>
      </S.Body>
    </S.Container>
  );
};

export default DatasetCard;
