import styled from 'styled-components';

type ContainerProps = JSX.IntrinsicElements['div'] & {
  direction?: 'row' | 'column',
  justify?: 'start' | 'end' | 'flex-start' | 'flex-end' | 'center' | 'space-between' | 'space-around',
  align?: 'start' | 'end' | 'flex-start' | 'flex-end' | 'center' | 'baseline' | 'stretch',
  unpadded?: boolean,
  nav?: boolean,
  cursor?: boolean,
  fullscreen? : boolean,
  nomargin?: boolean,
  unrounded?: boolean,
};

const Container = styled.div<ContainerProps>`
    display: flex;
    flex-direction: ${({ direction = 'column' }) => direction};
    justify-content: ${({ justify = 'flex-start' }) => justify};
    align-items: ${({ align = 'center' }) => align};
    width: ${({ theme }) => `calc(100% - ${theme.spacing.medium} - ${theme.spacing.medium})`};
    padding: ${({ unpadded, theme }) => (unpadded ? 0 : theme.spacing.medium)};
    padding-top: ${({ nav, unpadded, theme }) => {
    if (nav) return '15rem';
    if (unpadded) return 0;
    return theme.spacing.medium;
  }};
    border-radius: ${({ unrounded, theme }) => (unrounded ? 0 : theme.spacing.medium)};
    background-color: ${({ color }) => color};
    cursor: ${({ cursor }) => (cursor ? 'pointer' : 'default')};
    min-height: ${({ fullscreen }) => (fullscreen ? '88vh' : 'auto')};

    color: ${({ color = 'transparent', theme }) => {
    const { transparent, white, gray } = theme.colors;
    const { dark, light } = theme.text.colors;
    return color === transparent || color === white || color === gray ? dark : light;
  }};


  & > * {
      margin: ${({ theme, nomargin = false, direction = 'column' }) => {
    if (nomargin) return 0;
    const { small, medium } = theme.spacing;
    return direction === 'column' ? `${small} 0` : `0 ${medium}`;
  }
}};
  `;

export default Container;
