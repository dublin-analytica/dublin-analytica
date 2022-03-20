import { DataTable } from '@components';
import { Container } from '@containers';
import { useCartActions } from '@hooks';
import { useEffect, useState } from 'react';
import { useTheme } from 'styled-components';
import Cart from 'types/Cart';
import type { Theme } from '@styles/theme';

const Basket = () => {
  const { colors } = useTheme() as Theme;

  const [cart, setCart] = useState([] as Cart);
  const [selected, setSelected] = useState(new Set<string>());

  const { getCart } = useCartActions();

  const updateCart = () => getCart().then(setCart);

  useEffect(() => {
    updateCart();
  }, []);

  return (
    <Container fullscreen nav color={colors.primary}>
      <Container color={colors.white} direction="row">
        <Container>
          <DataTable datasets={cart} truncate={32} selected={selected} setSelected={setSelected} showTotalPrice />
        </Container>
        <Container>
          <h1>Checkout</h1>
        </Container>
      </Container>
    </Container>
  );
};

export default Basket;
