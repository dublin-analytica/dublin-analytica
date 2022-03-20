import Cart from 'types/Cart';
import useAPI from './useAPI';

const useCartActions = () => {
  const { get, post } = useAPI();

  const getCart = () => get('cart');

  const addToCart = (id: string, size: number) => (
    post('cart', { id, size })
  );

  const updateInCart = (id: string, size: number) => (
    post('cart/update', { id, size })
  );

  const removeFromCart = (id: string) => (
    updateInCart(id, 0)
  );

  const clearCart = async () => {
    const cart: Cart = await getCart();
    cart.forEach(({ id }) => removeFromCart(id));
  };

  const checkout = async (number: string, expiry: string, cvv: string) => (
    post('cart/checkout', { number, expiry, cvv })
  );

  return {
    getCart, addToCart, updateInCart, removeFromCart, clearCart, checkout,
  };
};

export default useCartActions;
