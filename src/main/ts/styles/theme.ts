export type Color = 'primary'
  | 'secondary'
  | 'transparent'
  | 'accent'
  | 'error'
  | 'warning'
  | 'info'
  | 'success'
  | 'white'
  | 'dark'
  | 'light';

export type TextColor = 'dark' | 'light';

export type Spacing = 'small' | 'medium' | 'large';

export type FontSize = 'default'

export type FontWeight = 'default' | 'bold';

export type Theme = {
  colors: { [key in Color]: string; };
  text: { colors: { [key in TextColor]: string } };
  spacing: { [key in Spacing]: string };
  typography: { size: { [key in FontSize]: string }, weight: { [key in FontWeight]: number } };
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

    white: '#ffffff',
    light: '#fafafa',
    dark: '#212121',
  },
  text: {
    colors: {
      dark: '#212121',
      light: '#ffffff',
    },
  },
  spacing: {
    small: '0.25rem',
    medium: '16px',
    large: '1rem',
  },

  typography: {
    size: {
      default: '16px',
    },
    weight: {
      default: 400,
      bold: 600,
    },
  },
};

export default theme;
