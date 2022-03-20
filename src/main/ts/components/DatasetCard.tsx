import { Container } from '@containers';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import type Dataset from 'types/Dataset';
import Button from './Button';

import PurchaseForm from './PurchaseForm';

type DatasetCardProps = Dataset

const S = {
  Container: styled(Container)`
    border: 1px solid ${({ theme }) => theme.colors.gray};
    width: 300px;
    height: 800px;
    cursor: pointer;
    margin-bottom: 2rem;
  `,

  Body: styled(Container)`
    padding: 1rem;
    margin: 0;
    height: 100%;

    & > * {
      width: 100%;
    }
  `,

  Img: styled.img`
    width: 300px;
    height: 300px;
    min-height: 300px;
    min-width: 300px;
    object-fit: cover;
    border-top-left-radius: 16px;
    border-top-right-radius: 16px;
    margin-bottom: 0.3rem;
  `,

  Title: styled(Button)`
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
};

const DatasetCard = ({
  id, name, image, description, size, unitPrice,
}: DatasetCardProps) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/dataset/${id}`);
  };

  return (
    <S.Container nomargin unpadded>
      <S.Img onClick={handleClick} src={image} alt={name} />
      <S.Body align="flex-start">
        <S.Title onClick={handleClick} unpadded variant="transparent">{name}</S.Title>
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
        <PurchaseForm id={id} unitPrice={unitPrice} size={size} />
      </S.Body>
    </S.Container>
  );
};

export default DatasetCard;
