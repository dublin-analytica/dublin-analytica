import { useTheme } from 'styled-components';
import { Container } from '@containers';
import { Logo, Title } from '@components';
import type { Theme } from '@styles/theme';

const Page404 = () => {
  const { text, colors } = useTheme() as Theme;

  return (
    <Container fullscreen color={colors.primary}>
      <Container direction="row" justify="space-around" style={{ marginTop: '2rem' }}>
        <Title color={text.colors.light} size="6rem">
          404
          <br />
          Page not found :(
        </Title>
        <Logo fill={text.colors.light} width={750} height={750} style={{}} />
      </Container>
    </Container>
  );
};

export default Page404;
