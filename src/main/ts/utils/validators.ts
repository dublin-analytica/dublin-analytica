const empty = (value: string) => value.length === 0;

export class Validator<T extends Record<string, string>> {
  constructor(public message: (values: T) => string, public validate: (values: T) => boolean) {}
}

export const nameValidator = new Validator(
  () => 'Enter your name',
  ({ name }) => !empty(name!),
);

export const emailValidator = new Validator(
  () => 'Enter a valid email.',
  ({ email }) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email!),
);

export const passwordValidator = new Validator(
  () => 'Use 8 characters or more for your password.',
  ({ password }) => password!.length >= 8,
);

export const confirmationValidator = new Validator(
  () => 'Those passwords didn’t match! Try again.',
  ({ password, confirmation }) => (
    passwordValidator.validate({ password: password! }) && password === confirmation
  ),
);
