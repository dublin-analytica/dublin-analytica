import { useNavigate } from 'react-router-dom';
import Button from './Button';

type LinkProps = { text: string; to: string; primary?: boolean, unpadded?: boolean };

const Link = ({
  text, to, primary, unpadded = false,
}: LinkProps) => {
  const navigate = useNavigate();

  return (
    <Button
      variant={primary ? 'primary' : 'transparent'}
      onClick={() => navigate(to)}
      unpadded={unpadded}
    >
      {text}
    </Button>
  );
};

export default Link;
