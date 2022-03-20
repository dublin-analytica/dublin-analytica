import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

import { AuthProvider } from '@context/AuthProvider';
import { CartProvider } from '@context/CartProvider';
import { Main } from '@containers';
import theme from '@styles/theme';

const App = () => (
  <div className="App">
    <BrowserRouter>
      <AuthProvider>
        <CartProvider>
          <ThemeProvider theme={theme}>
            <Main />
          </ThemeProvider>
        </CartProvider>
      </AuthProvider>
    </BrowserRouter>
  </div>
);

export default App;
