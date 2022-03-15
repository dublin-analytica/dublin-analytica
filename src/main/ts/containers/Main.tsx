import { Navbar } from '@components';
import theme from '@styles/theme';
import { useEffect, useState } from 'react';

import Container from './Container';
import Router from './Router';

const Main = () => {
  const [scrolled, setScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      const scrollTop = window.scrollY;
      console.log(scrollTop);
      if (scrollTop > 76) setScrolled(true);
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
