import { Container } from '@containers';
import styled from 'styled-components';

type StatsProps = { stats: { name: string, value: number }[] };

const S = {
  Value: styled.span`
  `,

  Name: styled.span`
    color: ${({ theme }) => theme.colors.text.secondary};
  `,

  Divider: styled.hr`
    border: none;
    border-top: 1px solid ${({ theme }) => theme.colors.text.secondary};
    margin: 0.5rem 0;
    transform: rotate(90deg);
  `,
};

const Stats = ({ stats }: StatsProps) => (
  <Container direction="row">
    {stats.map(({ name, value }) => (
      <div key={name}>
        <Container>
          <S.Value>{value}</S.Value>
          <S.Name>{name}</S.Name>
        </Container>
        <S.Divider />
      </div>
    ))}
  </Container>
);

export default Stats;
