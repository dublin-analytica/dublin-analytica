import { Navbar } from '@components';
import { useEffect, useState } from 'react';
import Router from './Router';

const Main = () => {
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
      <Navbar scrolled={scrolled} />
      <Router />
    </main>
  );
};

export default Main;
