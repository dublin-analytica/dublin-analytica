export type Color = 'primary' | 'secondary' | 'transparent' | 'accent' | 'error' | 'warning' | 'info' | 'success';
export type TextColor = 'dark' | 'light';

export type Theme = {
  colors: { [key in Color]: string; };
  text: { colors: { [key in TextColor]: string } };
}

const theme: Theme = {
  colors: {
    primary: '#53CA9D',
    secondary: '#ff9800',
    transparent: 'transparent',

    accent: '#9c27b0',
    error: '#f44336',
    warning: '#ffeb3b',
    info: '#2196f3',
    success: '#4caf50',
  },
  text: {
    colors: {
      dark: '#212121',
      light: '#ffffff',
    },
  },
};

export default theme;
