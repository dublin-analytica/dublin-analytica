import { DataTable, Sidebar, SplitView } from '@components';
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

  return (
    <SplitView>
      <Sidebar />
      <DataTable datasets={datasets} selected={selected} setSelected={setSelected} />
    </SplitView>
  );
};

export default DataDashboard;
