import { useAuth } from '@context/AuthProvider';

const Navbar = () => {
  const { user } = useAuth();

  return (
    <nav />
  );
};

export default Navbar;
