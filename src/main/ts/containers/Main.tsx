import { Navbar } from '@components';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import Router from './Router';

const Main = () => {
  const location = useLocation();

  const shouldRenderNavbar = () => {
    const noRenderPaths = ['/login', '/register'];
    const { pathname } = location;

    return !noRenderPaths.includes(pathname);
  };

  const [scrolled, setScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 76) setScrolled(true);
      else setScrolled(false);
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  return (
    <main>
      {shouldRenderNavbar() && <Navbar scrolled={scrolled} />}
      <Router />
    </main>
  );
};

export default Main;
