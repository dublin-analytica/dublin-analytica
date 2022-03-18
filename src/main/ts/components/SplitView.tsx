import { Container } from '@containers';
import { useTheme } from 'styled-components';

import type { Theme } from '@styles/theme';

type SplitViewProps = { children: [React.ReactNode, React.ReactNode] };

const SplitView = ({ children }: SplitViewProps) => {
  const { colors } = useTheme() as Theme;

  return (
    <Container nav color={colors.primary}>
      <Container nomargin unpadded color={colors.white} direction="row">
        <Container
          color={colors.gray}
          style={{
            borderTopRightRadius: 0,
            borderBottomRightRadius: 0,
            flexBasis: '30%',
          }}
        >
          {children[0]}
        </Container>
        <Container style={{ flexBasis: '80%' }}>
          {children[1]}
        </Container>
      </Container>
    </Container>
  );
};

export default SplitView;
