import React, { useContext, useMemo } from 'react';

import type Cart from 'types/Cart';

type CartProviderProps = { children: React.ReactNode };
type Context = [Cart | null, (cart: Cart) => void];

const CartContext = React.createContext<Context | null>(null);

const CartProvider = ({ children }: CartProviderProps) => {
  const [cart, setCart] = React.useState<Cart | null>(null);

  const context = useMemo((): Context => [cart, setCart], [cart]);

  return (
    <CartContext.Provider value={context}>
      {children}
    </CartContext.Provider>
  );
};

const useCart = (): Context => {
  const context = useContext(CartContext);
  if (!context) throw Error('useCart must be used within a CartProvider');
  return context;
};

export { CartProvider, useCart };
