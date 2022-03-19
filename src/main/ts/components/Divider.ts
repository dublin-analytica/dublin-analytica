import styled from 'styled-components';

const Divider = styled.hr`
    border: none;
    border-left: 1px solid ${({ theme }) => theme.colors.gray};
    height: 7rem;
    margin: 0.5rem 0;
  `;

export default Divider;
