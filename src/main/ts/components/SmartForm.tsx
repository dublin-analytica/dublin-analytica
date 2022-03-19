import React, { useEffect, useState } from 'react';
import type { Validator } from '@utils/validators';

import Button from './Button';
import ErrorToast from './ErrorToast';
import Form from './Form';
import Input from './Input';

type Field = {
  name: string,
  validator?: Validator<Record<string, string>>,
  type?: React.HTMLInputTypeAttribute,
  label: string
  placeholder?: string | undefined;
  value?: string | undefined;
};

export type SubmitAction = (
  values: Record<string, string>, setError: React.Dispatch<React.SetStateAction<string>>
) => void;

type SmartFormProps = {
  fields: Field[],
  action: string;
  children?: [React.ReactNode, React.ReactNode],
  onSubmit: SubmitAction
  validated?: boolean,
};

const SmartForm = ({
  fields, children, onSubmit, action, validated = false,
}: SmartFormProps) => {
  const createFieldObject = <T, >(init: T) => (
    fields.reduce((acc, { name }) => (
      { ...acc, [name]: init }
    ), {} as Record<string, T>)
  );

  const createSetter = <T, >(setter: React.Dispatch<React.SetStateAction<{}>>) => (
    (field: string, value: T) => setter((prevState) => ({ ...prevState, [field]: value }))
  );

  const [values, setValues] = useState(createFieldObject(''));
  const setValue = createSetter(setValues);

  useEffect(() => {
    fields.forEach(({ name, value }) => {
      if (value) setValue(name, value);
    });
  }, []);

  const validators = fields.reduce((acc, { name, validator }) => {
    const validate = validator?.validate ?? (() => true);

    return { ...acc, [name]: () => validate(values) };
  }, {} as { [key: string ]: () => boolean });

  const [error, setError] = useState('');

  const [valid, setValid] = useState(createFieldObject(false));
  const setValidField = createSetter(setValid);

  const [changed, setChanged] = useState(createFieldObject(false));
  const setChangedField = createSetter(setChanged);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setValue(name, value);
  };

  useEffect(() => {
    fields.forEach(({ name }) => {
      setValidField(name, validators[name]!());
      setChangedField(name, values[name] !== '');
    });
  }, [values]);

  const handleSubmit = () => {
    fields.forEach(({ name }) => setChangedField(name, true));

    if (fields.every(({ name, validator }) => {
      const message = validator?.message ?? (() => '');
      if (!valid[name]) setError(message(values));
      return valid[name];
    })) onSubmit(values, setError);
  };

  return (
    <Form onSubmit={handleSubmit}>
      {children ? children[0] : null ?? null}
      {fields.map(({
        name, label, type = 'text', placeholder = '',
      }) => (
        <Input
          key={name}
          name={name}
          type={type}
          label={label}
          value={values[name]}
          placeholder={placeholder}
          onChange={handleChange}
          valid={validated && valid[name]!}
          error={validated && changed[name]! && !valid[name]!}
        />
      ))}
      <ErrorToast message={error} />
      <Button type="submit">{action}</Button>
      {children ? children[1] : null ?? null}
    </Form>
  );
};

export default SmartForm;
