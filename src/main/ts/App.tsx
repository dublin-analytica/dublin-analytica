import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from './context/AuthProvider';
import Main from './containers/Main';

const App = () => (
  <div className="App">
    <BrowserRouter>
      <AuthProvider>
        <Main />
      </AuthProvider>
    </BrowserRouter>
  </div>
);

export default App;
