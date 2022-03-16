import { ExclamationCircleIcon } from '@heroicons/react/solid';
import type { Theme } from '@styles/theme';

import styled, { useTheme } from 'styled-components';

type ErrorProps = { message: string };

const S = {
  ErrorToast: styled.span`
    color: ${({ theme }) => theme.text.colors.light};
    font-size: ${({ theme }) => theme.typography.size.medium};
    font-weight: ${({ theme }) => theme.typography.weight.bold};
    margin-top: 0.5rem;
    background-color: ${({ theme }) => theme.colors.error};
    padding: 0.5rem;
    border-radius: 5px;
    margin-bottom: 0.5rem;
    display: block;
  `,
};

const ErrorToast = ({ message }: ErrorProps) => {
  const theme = useTheme() as Theme;
  return (
    <div>
      {message.length ? (
        <S.ErrorToast>
          <ExclamationCircleIcon width={16} height={16} fill={theme.text.colors.light} />
          {message}
        </S.ErrorToast>
      ) : null}
    </div>
  );
};

export default ErrorToast;
