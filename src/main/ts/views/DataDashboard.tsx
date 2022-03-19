import { Sidebar, SplitView } from '@components';
import Table from '@components/Table';
import { useDatasetActions } from '@hooks';
import Dataset from 'types/Dataset';
import { useEffect, useState } from 'react';

const DataDashboard = () => {
  const { getDatasets } = useDatasetActions();
  const [datasets, setDatasets] = useState([] as Dataset[]);
  const [selected, setSelected] = useState(new Set<string>());

  const updateDatasets = () => getDatasets().then(setDatasets);

  useEffect(() => {
    updateDatasets();
  }, []);

  const headers = ['Name', 'Unit Price', 'Description', 'Hidden', 'Datapoints'];
  const rows = datasets.map(({
    id, name, unitPrice, description, hidden, size,
  }) => ({
    id,
    values: [
      name,
      `€${unitPrice.toFixed(5)}`,
      `${description.substring(0, 64)}...`,
      hidden ? 'Yes' : 'No',
      `${size}`,
    ],
  }));

  return (
    <SplitView>
      <Sidebar />
      <Table headers={headers} rows={rows} selected={selected} setSelected={setSelected} />
    </SplitView>
  );
};

export default DataDashboard;
