import { BrowserRouter } from 'react-router-dom';
import { UserProvider } from './context/UserProvider';
import Main from './containers/Main';

const App = () => (
  <div className="App">
    <BrowserRouter>
      <UserProvider>
        <Main />
      </UserProvider>
    </BrowserRouter>
  </div>
);

export default App;
