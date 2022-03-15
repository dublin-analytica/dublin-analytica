import styled, { useTheme } from 'styled-components';
import { Container } from '@containers';
import { Logo, Title } from '@components';
import type { Theme } from '@styles/theme';

const S = {
  Description: styled.p`
    font-size: 1.5rem;
    color: ${({ theme }) => theme.text.colors.light};
    padding: 0 5rem;
    width: 60%;
    margin-right: auto;
    position: absolute;
    top: 45rem;
    left: 5rem;
  `,
};

const Landing = () => {
  const { colors, text } = useTheme() as Theme;

  return (
    <div>
      <Container nav color={colors.primary}>
        <Container direction="row" justify="space-around">
          <Title color={text.colors.light} size="6rem" />
          <Logo fill={text.colors.light} width={750} height={750} />
        </Container>
        <Container>
          <S.Description>
            At Dublin Analytica we use data modelling and psychographic profiling
            to grow audiences, identify key influencers, and connect with people
            in ways that move them to action. Our unique data sets and
            unparalleled modeling techniques help organisations across Ireland
            build better relationships with their target audience across all media
            platforms.
          </S.Description>
        </Container>
      </Container>
    </div>
  );
};

export default Landing;
