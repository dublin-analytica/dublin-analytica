import { Container } from '@containers';
import { Button, Logo } from '@components';
import { useTheme } from 'styled-components';
import type { Theme } from '@styles/theme';

const Landing = () => {
  const { colors, text } = useTheme() as Theme;

  return (
    <div>
      <Container color={colors.primary}>
        <h1>Landing</h1>
        <Logo fill={text.colors.light} width={250} height={250} />
        <Button>
          Marketplace
        </Button>
      </Container>
    </div>
  );
};

export default Landing;
