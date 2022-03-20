import { DatasetForm } from '@components';
import { useDatasetActions } from '@hooks';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import Dataset from 'types/Dataset';

const EditDataset = () => {
  const [dataset, setDataset] = useState({} as Dataset);
  const { id } = useParams();

  const { getDataset } = useDatasetActions();

  const updateDataset = () => getDataset(id!).then(setDataset);

  useEffect(() => {
    updateDataset();
  }, []);

  return <DatasetForm dataset={dataset} />;
};

export default EditDataset;
