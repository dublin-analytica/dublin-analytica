import { PurchaseForm } from '@components';
import { useDatasetActions } from '@hooks';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import type DatasetType from 'types/Dataset';

const Dataset = () => {
  const [dataset, setDataset] = useState({} as DatasetType);
  const { id } = useParams();
  const navigate = useNavigate();

  const { getDataset } = useDatasetActions();

  const updateDataset = () => (
    getDataset(id!).then(setDataset).catch(() => navigate('/404'))
  );

  useEffect(() => {
    updateDataset();
  }, []);

  const { unitPrice, size } = dataset;

  return <PurchaseForm id={id!} unitPrice={unitPrice} size={size} />;
};

export default Dataset;
