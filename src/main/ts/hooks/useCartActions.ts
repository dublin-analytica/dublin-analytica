import Cart from 'types/Cart';
import useAPI from './useAPI';

const useCartActions = () => {
  const { get, post } = useAPI();

  const getCart = () => get('/cart');

  const addToCart = (id: string, size: number) => (
    post('/cart', { id, size })
  );

  const updateCart = (id: string, size: number) => (
    post('/cart/update', { id, size })
  );

  const removeFromCart = (id: string) => (
    updateCart(id, 0)
  );

  const clearCart = async () => {
    const cart: Cart = await getCart();
    cart.forEach(({ id }) => removeFromCart(id));
  };

  const checkout = async (cvv: string, number: string, expiry: string) => (
    post('/cart/checkout', { cvv, number, expiry })
  );

  return {
    getCart, addToCart, updateCart, removeFromCart, clearCart, checkout,
  };
};

export default useCartActions;
