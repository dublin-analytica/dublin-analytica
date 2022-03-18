import { Container } from '@containers';
import styled from 'styled-components';

type StatsProps = { stats: { name: string, value: string }[] };

const S = {
  Value: styled.span`
    font-size: 3rem;
    font-weight: bold;
  `,

  Name: styled.span`
    color: ${({ theme }) => theme.text.colors.secondary};
  `,

  Divider: styled.hr`
    border: none;
    border-left: 1px solid ${({ theme }) => theme.colors.gray};
    height: 7rem;
    margin: 0.5rem 0;
  `,

  StatContainer: styled(Container)`
    padding: 0;
    margin: 0;
  `,
};

const Stats = ({ stats }: StatsProps) => (
  <Container direction="row">
    {stats.map(({ name, value }, i) => (
      <S.StatContainer direction="row" key={name}>
        <Container>
          <S.Value>{value}</S.Value>
          <S.Name>{name}</S.Name>
        </Container>
        {i !== stats.length - 1 && <S.Divider />}
      </S.StatContainer>
    ))}
  </Container>
);

export default Stats;
