import styled from 'styled-components';
import { BeakerIcon } from '@heroicons/react/solid';

const H1 = styled.h1`
  font-size: 1.5em;
  text-align: center;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  `;

const Dashboard = () => (
  <H1>
    Dashboard
    <BeakerIcon />
  </H1>
);

export default Dashboard;
