import React, { useEffect } from 'react';
import styled from 'styled-components';

type TableProps = {
  setSelected?: React.Dispatch<React.SetStateAction<Set<string>>> | undefined,
  selected?: Set<string> | undefined,
  headers: string[],
  rows: { id: string, values: string[] }[]
};

const S = {
  Table: styled.table`
    width: 90%;
    border-collapse: collapse;
  `,

  THead: styled.thead`
    border-bottom: 1px solid ${({ theme }) => theme.colors.gray};
  `,

  TH: styled.th`
    text-align: left;
  `,

  TR: styled.tr`
    height: 5rem;
    tr:first-child td:first-child { border-top-left-radius: 10px; }
    tr:first-child td:last-child { border-top-right-radius: 10px; }
    tr:last-child td:first-child { border-bottom-left-radius: 10px; }
    tr:last-child td:last-child { border-bottom-right-radius: 10px; }
    cursor: pointer;
    

    &.selected {
      background-color: ${({ theme }) => theme.colors.gray};
    }
  `,
};

const Table = ({
  setSelected, selected, headers, rows,
}: TableProps) => {
  const toggleSelected = (id: string) => () => {
    if (setSelected) {
      setSelected((prevSelected) => {
        const newSelected = new Set(prevSelected);
        if (newSelected.has(id)) newSelected.delete(id);
        else newSelected.add(id);
        return newSelected;
      });
    }
  };

  const toggleAll = () => {
    if (setSelected) {
      setSelected((prevSelected) => {
        const newSelected = new Set(prevSelected);
        if (newSelected.size === rows.length) newSelected.clear();
        else rows.forEach(({ id }) => newSelected.add(id));
        return newSelected;
      });
    }
  };

  useEffect(() => {
    if (setSelected) {
      setSelected(selected!);
      return () => setSelected(new Set());
    }
    return () => {};
  }, [selected]);

  const allSelected = () => selected?.size === rows.length;

  return (
    <S.Table>
      <S.THead>
        <S.TR>
          {selected && <S.TH><input aria-label="Select All" type="checkbox" checked={allSelected()} onChange={toggleAll} /></S.TH>}
          {headers.map((header) => <S.TH key={header}>{header}</S.TH>)}
        </S.TR>
      </S.THead>
      <tbody>
        {rows.map(({ id, values }) => (
          selected && (
          <S.TR key={id} onClick={toggleSelected(id)} className={selected?.has(id) ? 'selected' : ''}>
            <td>
              <input
                aria-label="Select"
                type="checkbox"
                checked={selected?.has(id)}
                onChange={toggleSelected(id)}
              />
            </td>
            {values.map((value) => <td key={value}>{value}</td>)}
          </S.TR>
          )
        ))}
      </tbody>
    </S.Table>
  );
};

export default Table;
