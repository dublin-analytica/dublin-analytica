// eslint-disable-next-line import/prefer-default-export
export const toTitleCase = (str: string) => (
  str.slice(0, 1).toUpperCase() + str.slice(1).toLowerCase()
);
