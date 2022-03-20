import { DatasetCard } from '@components';
import { Container } from '@containers';
import { useDatasetActions } from '@hooks';
import type Dataset from 'types/Dataset';
import { useEffect, useState } from 'react';
import styled, { useTheme } from 'styled-components';

import type { Theme } from '@styles/theme';

const S = {
  Grid: styled(Container)`
    flex-wrap: wrap;
    flex: 1;
    justify-content: space-around;
  `,
};

const Marketplace = () => {
  const { colors } = useTheme() as Theme;
  const [datasets, setDatasets] = useState([] as Dataset[]);
  const { getDatasets } = useDatasetActions();

  const updateDatasets = () => getDatasets().then(setDatasets);

  useEffect(() => {
    updateDatasets();
  }, []);

  return (
    <Container nav color={colors.primary}>
      <Container color={colors.white}>
        <S.Grid direction="row">
          {datasets.filter(({ hidden }) => !hidden).map((dataset) => (
            <DatasetCard key={dataset.id} {...dataset} />
          ))}
        </S.Grid>
      </Container>
    </Container>
  );
};

export default Marketplace;
