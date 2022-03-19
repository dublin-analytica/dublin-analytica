import styled from 'styled-components';

type TitleProps = {
  color: string, size: string, onClick?: React.MouseEventHandler<HTMLHeadingElement>,
  children?: React.ReactNode
};

const S = {
  Title: styled.div<TitleProps>`
    display: flex;
    flex-direction: column;
    align-items: left;
    cursor: ${({ onClick }) => (onClick ? 'pointer' : 'default')};

    & h1 {
      font-size: ${({ size }) => size};
      font-weight: 600;
      color: ${({ color, theme }) => color ?? theme.text.colors.dark};
      margin: 0;
      padding: 0;
      line-height: ${({ size }) => size};
    }
  `,
};

const Title = ({
  color, size, onClick, children }: TitleProps) => {
  const handleClick = (e: React.MouseEvent<HTMLHeadingElement, MouseEvent>) => {
    if (onClick) onClick(e);
  };

  // Must be a nicer way to do this...
  return (
    <S.Title color={color} size={size} onClick={handleClick}>
      <h1>
        { children
          || (
          <>
            Dublin
            <br />
            Analytica
          </>
          )}
      </h1>
    </S.Title>
  );
};

export default Title;
