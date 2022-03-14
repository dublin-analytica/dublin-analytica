import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

import { AuthProvider } from '@context/AuthProvider';
import { Main } from '@containers';
import theme from '@styles/theme';

const App = () => (
  <div className="App">
    <BrowserRouter>
      <AuthProvider>
        <ThemeProvider theme={theme}>
          <Main />
        </ThemeProvider>
      </AuthProvider>
    </BrowserRouter>
  </div>
);

export default App;
