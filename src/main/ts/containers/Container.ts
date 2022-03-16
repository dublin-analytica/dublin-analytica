import styled from 'styled-components';

type ContainerProps = JSX.IntrinsicElements['div'] & {
  direction?: 'row' | 'column',
  justify?: 'start' | 'end' | 'flex-start' | 'flex-end' | 'center' | 'space-between' | 'space-around',
  unpadded?: boolean,
  nav?: boolean,
  cursor?: boolean,
  fullscreen? : boolean,
};

const Container = styled.div<ContainerProps>`
    display: flex;
    flex-direction: ${({ direction = 'column' }) => direction};
    justify-content: ${({ justify = 'flex-start' }) => justify};
    align-items: center;
    width: ${({ theme }) => `calc(100% - ${theme.spacing.medium} - ${theme.spacing.medium})`};
    padding: ${({ unpadded, nav, theme }) => (unpadded ? 0 : `${nav ? '5rem' : theme.spacing.medium} ${theme.spacing.medium}`)};
    border-radius: ${({ theme }) => theme.spacing.medium};
    background-color: ${({ color }) => color};
    cursor: ${({ cursor }) => (cursor ? 'pointer' : 'default')};
    color: ${({ color = 'transparent', theme }) => {
    const { transparent, white } = theme.colors;
    const { dark, light } = theme.text.colors;
    return color === transparent || color === white ? dark : light;
  }};

    & > * {
      margin: ${({ theme, direction = 'column' }) => {
    const { small, medium } = theme.spacing;
    return direction === 'column' ? `${small} 0` : `0 ${medium}`;
  }
}};
  `;

export default Container;
