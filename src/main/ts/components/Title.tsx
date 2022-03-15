import styled from 'styled-components';

type TitleProps = { color: string, size: number };

const S = {
  Title: styled.div<TitleProps>`
    display: flex;
    flex-direction: column;
    align-items: left;

    & h1 {
      font-size: ${({ size }) => `${size}rem`};
      font-weight: 600;
      color: ${({ color, theme }) => color ?? theme.text.colors.dark};
      margin: 0;
      padding: 0;
      line-height: ${({ size }) => `calc(${size} / 4)`};
    }
  `,
};

const Title = ({ color, size }: TitleProps) => (
  <S.Title color={color} size={size}>
    <h1>
      Dublin
      <br />
      Analytica
    </h1>
  </S.Title>
);

export default Title;
