import { Button, Checkout, DataTable } from '@components';
import { Container } from '@containers';
import { useCartActions, useDatasetActions } from '@hooks';
import { useEffect, useMemo, useState } from 'react';
import { useTheme } from 'styled-components';
import Cart from 'types/Cart';
import type { Theme } from '@styles/theme';

const Basket = () => {
  const { colors } = useTheme() as Theme;

  const [pending, setPending] = useState(false);
  const [cart, setCart] = useState([] as Cart);
  const [selected, setSelected] = useState(new Set<string>());

  const { getCart, removeFromCart, updateInCart } = useCartActions();
  const { getDataset } = useDatasetActions();

  const updateCart = () => getCart().then(setCart);

  const totalPrice = useMemo(() => (
    cart.reduce((acc, { unitPrice, size }) => acc + unitPrice * size, 0).toFixed(2)
  ), [cart]);

  useEffect(() => {
    updateCart();
  }, []);

  const remove = () => {
    Promise.all(Array.from(selected).map((id) => removeFromCart(id)))
      .then(() => {
        updateCart();
        setSelected(new Set());
      });
  };

  const add = (n: number) => async () => {
    if (!pending) {
      setPending(true);

      await Promise.all(
        Array.from(selected).map(async (id) => {
          const item = cart.find((item) => item.id === id);
          const { size } = item!;
          const available: number = (await getDataset(id)).size;

          if ((n > 0 && size + n <= available) || (n < 0 && size > -n)) {
            return updateInCart(id, size + n);
          }

          return new Promise<void>((resolve) => {
            resolve();
          });
        }),
      );

      updateCart();
      setPending(false);
    }
  };

  const subtract = (n: number) => add(-n);

  return (
    <Container fullscreen nav color={colors.primary}>
      <Container color={colors.white} direction="row">
        <Container>
          <h1>My Basket</h1>
          <Container direction="row" justify="space-around">
            <Button disabled={selected.size === 0} variant="transparent" outline onClick={subtract(100)}>- 100</Button>
            <Button disabled={selected.size === 0} variant="transparent" outline onClick={remove}>Remove from Cart</Button>
            <Button disabled={selected.size === 0} variant="transparent" outline onClick={add(100)}>+ 100</Button>
          </Container>
          <DataTable
            datasets={cart}
            truncate={24}
            selected={selected}
            setSelected={setSelected}
            showTotalPrice
          />
          <Container direction="row">
            <h2>
              Total: €
              {totalPrice}
            </h2>
          </Container>
        </Container>
        <Container>
          <h1>Checkout</h1>
          <Checkout />
        </Container>
      </Container>
    </Container>
  );
};

export default Basket;
