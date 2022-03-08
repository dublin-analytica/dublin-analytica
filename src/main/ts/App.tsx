import { BrowserRouter } from 'react-router-dom';
import Main from './containers/Main';

const App = () => (
  <div className="App">
    <BrowserRouter><Main /></BrowserRouter>
  </div>
);

export default App;
