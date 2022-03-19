export const toTitleCase = (str: string) => (
  str.slice(0, 1).toUpperCase() + str.slice(1).toLowerCase()
);

export const formatDate = (timestamp: number) => {
  const getArgs = (diff: number): [number, Intl.RelativeTimeFormatUnit] => {
    const seconds = Math.floor(diff / 1000);

    if (seconds < 60) return [seconds, 'second'];
    if (seconds < 3600) return [seconds / 60, 'minute'];
    if (seconds < 86400) return [seconds / 3600, 'hour'];
    if (seconds < 604800) return [seconds / 86400, 'day'];
    if (seconds < 2419200) return [seconds / 604800, 'week'];
    if (seconds < 29030400) return [seconds / 2419200, 'month'];
    return [seconds / 2419200, 'year'];
  };

  const formatter = new Intl.RelativeTimeFormat('en', { style: 'narrow' });
  const date = new Date(timestamp);
  const diff = Math.round((new Date().getTime() - date.getTime()));

  const [magnitude, unit] = getArgs(diff);
  return formatter.format(-Math.round(magnitude), unit);
};
