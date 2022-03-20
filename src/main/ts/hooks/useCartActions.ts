import { useCart } from '@context/CartProvider';
import useAPI from './useAPI';

const useCartActions = () => {
  const { get, post, patch } = useAPI();
  const [cart, setCart] = useCart();

  const getCart = () => get('cart').then(setCart);

  const addToCart = (id: string, size: number) => (
    post('cart', { id, size }).then(getCart)
  );

  const updateInCart = (id: string, size: number) => (
    patch('cart', { id, size }).then(getCart)
  );

  const removeFromCart = (id: string) => (
    updateInCart(id, 0).then(getCart)
  );

  const clearCart = async () => {
    if (!cart) return;
    Promise.all(cart.map(({ id }) => removeFromCart(id))).then(getCart);
  };

  const checkout = async (number: string, expiry: string, cvv: string) => (
    post('cart/checkout', { number, expiry, cvv }).then(getCart)
  );

  return {
    getCart, addToCart, updateInCart, removeFromCart, clearCart, checkout,
  };
};

export default useCartActions;
