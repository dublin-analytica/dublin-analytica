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

export const cardNumberValidator = new Validator(
  () => 'Enter a valid card number.',
  ({ cardNumber }) => /^\d{16}$/.test(cardNumber?.replace(/[\s-]/g, '')!),
);

export const cardExpiryValidator = new Validator(
  () => 'Enter a valid card expiry date.',
  ({ expiry }) => (
    /^\d{2}\/\d{2}$/.test(expiry!)
    && Number(expiry?.substring(0, 2)) > 0
    && Number(expiry?.substring(0, 2)) <= 12),
);

export const cardCvvValidator = new Validator(
  () => 'Enter a valid card CVV.',
  ({ cvv }) => /^\d{3}$/.test(cvv!),
);
