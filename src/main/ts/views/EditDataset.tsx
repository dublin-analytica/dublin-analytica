import { DatasetForm } from '@components';
import { useDatasetActions } from '@hooks';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Dataset from 'types/Dataset';

const EditDataset = () => {
  const [dataset, setDataset] = useState({} as Dataset);
  const { id } = useParams();
  const navigate = useNavigate();

  const { getDataset } = useDatasetActions();

  const updateDataset = () => (
    getDataset(id!).then(setDataset).catch(() => navigate('/404'))
  );

  useEffect(() => {
    updateDataset();
  }, []);

  return <DatasetForm dataset={dataset} />;
};

export default EditDataset;
