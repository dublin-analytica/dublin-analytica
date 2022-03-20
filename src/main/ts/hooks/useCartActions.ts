import Cart from 'types/Cart';
import useAPI from './useAPI';

const useCartActions = () => {
  const { get, post, patch } = useAPI();

  const getCart = () => get('cart');

  const addToCart = (id: string, size: number) => {
    console.log(size);
    return post('cart', { id, size });
  };

  const updateInCart = (id: string, size: number) => (
    patch('cart', { id, size })
  );

  const removeFromCart = (id: string) => (
    updateInCart(id, 0)
  );

  const clearCart = async () => {
    const cart: Cart = await getCart();
    cart.forEach(({ id }) => removeFromCart(id));
  };

  const checkout = async (cvv: string, number: string, expiry: string) => (
    post('cart/checkout', { cvv, number, expiry })
  );

  return {
    getCart, addToCart, updateInCart, removeFromCart, clearCart, checkout,
  };
};

export default useCartActions;
