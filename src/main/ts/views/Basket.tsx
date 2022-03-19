import { DatasetCard } from '@components';
import { Container } from '@containers';
import { useCartActions } from '@hooks';
import { useEffect, useState } from 'react';
import Cart from 'types/Cart';

const Basket = () => {
  const [cart, setCart] = useState([] as Cart);
  const { getCart } = useCartActions();

  const updateCart = () => getCart().then(setCart);

  useEffect(() => {
    updateCart();
  }, []);

  return (
    <Container fullscreen nav color="primary">
      {cart.map((item) => (
        <DatasetCard key={item.id} {...item} />
      ))}
    </Container>
  );
};

export default Basket;
