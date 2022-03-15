import { Container } from '@containers';
import { Button, Logo } from '@components';
import { useTheme } from 'styled-components';
import type { Theme } from '@styles/theme';

const Landing = () => {
  const { colors, text } = useTheme() as Theme;

  return (
    <div>
      <Container nav color={colors.primary}>
        <Logo fill={text.colors.light} />
        <Button>
          Marketplace
        </Button>
      </Container>
    </div>
  );
};

export default Landing;
