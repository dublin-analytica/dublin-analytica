import { Container } from '@containers';
import { useCartActions } from '@hooks';
import { useEffect, useState } from 'react';
import { useTheme } from 'styled-components';
// import styled from 'styled-components';
import type Dataset from 'types/Dataset';
import type { Theme } from '@styles/theme'

type DatasetCardProps = Dataset

const S = {
};

const DatasetCard = ({
  id, name, image, description, size, unitPrice,
}: DatasetCardProps) => {
  const [datapoints, setDatapoints] = useState(100);
  const [value, setValue] = useState('');

  const { colors } = useTheme() as Theme;

  const { addToCart } = useCartActions();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    const value = Number(input);
    if (input === '' || (Number.isInteger(value) && value > 0 && value <= size)) {
      setValue(e.target.value.trim().replace(/\./g, ''));
    }
  };

  const handleClick = () => {
    addToCart(id, datapoints);
  };

  useEffect(() => {
    if (value === '') setDatapoints(100);
    else setDatapoints(parseInt(value, 10));
  }, [value]);

  return (
    <Container nav fullscreen color={colors.primary}>
      
    </Container>
  );
};

export default DatasetCard;
