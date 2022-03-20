import {
  Button,
  DataTable, Sidebar, SplitView, Stats,
} from '@components';
import { useDatasetActions } from '@hooks';
import Dataset from 'types/Dataset';
import { useEffect, useState } from 'react';
import { Container } from '@containers';
import { useNavigate } from 'react-router-dom';

const DataDashboard = () => {
  const navigate = useNavigate();

  const { getDatasets, updateDataset } = useDatasetActions();

  const [pending, setPending] = useState(false);
  const [datasets, setDatasets] = useState([] as Dataset[]);
  const [selected, setSelected] = useState(new Set<string>());

  const updateDatasets = () => getDatasets().then(setDatasets);

  useEffect(() => {
    updateDatasets();
  }, []);

  const datapoints = datasets.reduce((acc, { size }) => acc + size, 0);
  const forSale = datasets.filter(({ hidden }) => !hidden).length;
  const total = datasets.length;

  const stats = [
    { name: 'For Sale/Total Datasets', value: `${forSale}/${total}` },
    { name: 'Total Datapoints', value: datapoints.toString() },
    { name: 'Average Datapoints in a Set', value: `${Math.round(datapoints / total)}` },
  ];

  const update = async (hidden: boolean) => {
    if (!pending) {
      setPending(true);

      await Promise.all(
        Array.from(selected).map(async (id) => updateDataset(id, { hidden })),
      );

      updateDatasets();

      setPending(false);
    }
  };

  const show = () => update(false);
  const hide = () => update(true);

  const edit = (id: string) => navigate(`/editdataset?id=${id}`);

  return (
    <SplitView>
      <Sidebar />
      <Container>
        <Stats stats={stats} />
        <Container direction="column" align="flex-start" style={{ width: '90%' }}>
          <Container unpadded direction="row" style={{ marginTop: '1rem' }}>
            <h2>Hide/Show Datasets: </h2>
            <Button
              disabled={selected.size === 0
                || Array.from(selected).every((id) => !datasets.find((d) => d.id === id)?.hidden)}
              outline
              onClick={show}
              name="show"
              variant="transparent"
            >
              Show
            </Button>
            <Button
              disabled={selected.size === 0
              || Array.from(selected).every((id) => datasets.find((d) => d.id === id)?.hidden)}
              outline
              onClick={hide}
              name="hide"
              variant="transparent"
            >
              Hide
            </Button>
            <Button style={{ marginLeft: 'auto' }} onClick={() => navigate('/adddataset')}>
              Add Dataset
            </Button>
          </Container>
        </Container>
        <DataTable
          action={edit}
          actionName="Edit"
          datasets={datasets}
          selected={selected}
          setSelected={setSelected}
          showHidden
        />
      </Container>
    </SplitView>
  );
};

export default DataDashboard;
