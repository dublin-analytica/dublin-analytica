import { BrowserRouter } from 'react-router-dom';
import Router from './containers/Router';

const App = () => (
  <div className="App">
    <BrowserRouter><Router /></BrowserRouter>
  </div>
);

export default App;
