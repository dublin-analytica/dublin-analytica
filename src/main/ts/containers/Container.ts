import styled from 'styled-components';

type ContainerProps = JSX.IntrinsicElements['div'] & {
  direction?: 'row' | 'column',
};

const Container = styled.div<ContainerProps>`
  display: flex;
  flex-direction: ${({ direction = 'column' }) => direction};
  flex-wrap: wrap;
  justify-content: ${({ direction = 'column' }) => (direction === 'row' ? 'space-around' : 'flex-start')};
  align-items: center;
  width: calc(100% - 2rem);
  padding: 1rem;
  border-radius: 16px;
  background-color: ${({ color }) => color};
  color: ${({ color = 'transparent', theme }) => {
    const { transparent } = theme.colors;
    const { dark, light } = theme.text.colors;
    return color === transparent ? dark : light;
  }};;
`;

export default Container;
