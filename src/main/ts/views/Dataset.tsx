import { PurchaseForm } from '@components';
import { Container } from '@containers';
import { useDatasetActions } from '@hooks';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styled, { useTheme } from 'styled-components';
import type { Theme } from '@styles/theme';

import type DatasetType from 'types/Dataset';

const S = {
  Image: styled.img`
    width: 500px;
    height: 500px;
    object-fit: contain;
    border-radius: 16px;
  `,
};

const Dataset = () => {
  const [dataset, setDataset] = useState({} as DatasetType);
  const { colors } = useTheme() as Theme;

  const { id } = useParams();
  const navigate = useNavigate();

  const { getDataset } = useDatasetActions();

  const updateDataset = () => (
    getDataset(id!).then(setDataset).catch(() => navigate('/404'))
  );

  useEffect(() => {
    updateDataset();
  }, []);

  const {
    name, unitPrice, size, image, description,
  } = dataset;

  return (
    <Container nav color={colors.primary}>
      <Container style={{ alignItems: 'stretch' }} color={colors.white} direction="row">
        <S.Image src={image} alt={name} />
        <PurchaseForm variant="large" id={id!} unitPrice={unitPrice} size={size} name={name} description={description} />
      </Container>
    </Container>
  );
};

export default Dataset;
