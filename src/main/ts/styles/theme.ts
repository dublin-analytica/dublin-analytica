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
  | 'light'
  | 'gray';

export type TextColor = 'dark' | 'light' | 'secondary';

export type Spacing = 'small' | 'medium' | 'large';

export type FontSize = 'small' | 'default' | 'large';

export type FontWeight = 'default' | 'bold';

export type Theme = {
  colors: { [key in Color]: string; };
  text: { colors: { [key in TextColor]: string } };
  spacing: { [key in Spacing]: string };
  typography: { size: { [key in FontSize]: string }, weight: { [key in FontWeight]: number } };
}

const theme: Theme = {
  colors: {
    primary: '#53ca9d',
    secondary: '#ff9800',
    transparent: 'transparent',

    accent: '#9c27b0',
    error: '#ef5350',
    warning: '#ffeb3b',
    info: '#2196f3',
    success: '#4caf50',

    white: '#ffffff',
    gray: '#f1f1f1',
    light: '#fafafa',
    dark: '#212121',
  },
  text: {
    colors: {
      dark: '#212121',
      light: '#ffffff',
      secondary: '#c4c4c4',
    },
  },
  spacing: {
    small: '0.25rem',
    medium: '16px',
    large: '1rem',
  },

  typography: {
    size: {
      small: '12px',
      default: '16px',
      large: '32px',
    },
    weight: {
      default: 400,
      bold: 600,
    },
  },
};

export default theme;
