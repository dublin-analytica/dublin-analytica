import { Container } from '@containers';
import React from 'react';
import styled from 'styled-components';
import Dataset from 'types/Dataset';
import Table from './Table';

type DataTableProps = {
  datasets: Dataset[],
  setSelected?: React.Dispatch<React.SetStateAction<Set<string>>>
  selected?: Set<string>,
  showHidden?: boolean,
  truncate?: number,
  showTotalPrice?: boolean,
};

const S = {
  H1: styled.h1`
    color: ${({ theme }) => theme.text.colors.secondary};
  `,
};

const DataTable = ({
  datasets, setSelected, selected, showHidden = false, showTotalPrice = false, truncate = 64,
}: DataTableProps) => {
  const headers = [
    'Name', showTotalPrice ? 'Price' : 'Unit Price', 'Description', ...(showHidden ? ['Hidden'] : []), 'Datapoints',
  ];

  const rows = datasets.map(({
    id, name, unitPrice, description, hidden, size,
  }) => ({
    id,
    values: [
      name,
      `€${(unitPrice * (showTotalPrice ? size : 1)).toFixed(showTotalPrice ? 2 : 5)}`,
      `${description.substring(0, truncate)}...`,
      ...(showHidden ? [hidden ? 'Yes' : 'No'] : []),
      `${size}`,
    ],
  }));

  return (
    <Container style={{ marginTop: '1rem' }}>
      {datasets.length === 0 && <S.H1>You haven&apos;t added anything to your basket!</S.H1>}
      {datasets.length > 0
        && <Table setSelected={setSelected} selected={selected} headers={headers} rows={rows} />}
    </Container>
  );
};

export default DataTable;
