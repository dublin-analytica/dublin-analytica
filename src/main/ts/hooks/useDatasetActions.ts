import Dataset from 'types/Dataset';
import DatasetDTO from 'types/DTO';
import useAPI from './useAPI';

const useDatasetActions = () => {
  const { get, post } = useAPI();

  const getDatasets = () => get('datasets');

  const getDataset = (id: string) => get(`datasets/${id}`);

  const addDataset = (dataset: DatasetDTO) => post('datasets', dataset);

  const updateDataset = (id: string, dataset: Partial<Dataset>) => post(`datasets/${id}`, dataset);

  return {
    getDatasets, getDataset, addDataset, updateDataset,
  };
};

export default useDatasetActions;
