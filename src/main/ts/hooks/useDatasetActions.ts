import Dataset from 'types/Dataset';
import useAPI from './useAPI';

const useDatasetActions = () => {
  const { get, post } = useAPI();

  const getDatasets = () => get('datasets');

  const getDataset = (id: string) => {
    get(`datasets/${id}`);
  };

  const updateDataset = (id: string, dataset: Partial<Dataset>) => (
    post(`datasets/${id}`, dataset)
  );

  return { getDatasets, getDataset };
};

export default useDatasetActions;
