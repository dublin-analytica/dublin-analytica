import { useTheme } from 'styled-components';

import { SmartForm as Form } from '@components';
import { Container } from '@containers';
import type { Theme } from '@styles/theme';
import { useNavigate } from 'react-router-dom';
import { SubmitAction } from '@components/SmartForm';
import {
  nameValidator, priceValidator, imageUrlValidator, linkUrlValidator,
} from '@utils/validators';
import { useDatasetActions } from '@hooks';
import Dataset from 'types/Dataset';

type DatasetFormProps = {
  dataset?: Partial<Dataset>
};

const DatasetForm = ({ dataset = {} }: DatasetFormProps) => {
  const navigate = useNavigate();
  const theme = useTheme() as Theme;
  const { addDataset, updateDataset } = useDatasetActions();

  const {
    id, name, description, image, link, unitPrice,
  } = dataset;

  const fields = [
    {
      name: 'name',
      label: 'Name',
      validator: nameValidator,
      placeholder: 'Dataset Name',
      value: name,
    },
    {
      name: 'description',
      label: 'Description',
      placeholder: 'Lorem ipsum dolor sit amet...',
      validator: nameValidator,
      value: description,
    },
    {
      name: 'image',
      label: 'Image URL',
      placeholder: 'https://images.unsplash.com/photo-1597589827317-4c6d6e0a90bd',
      validator: imageUrlValidator,
      value: image,
    },
    {
      name: 'link',
      label: 'Dataset URL',
      placeholder: 'https://pastebin.com/raw/QQgqV98Z',
      validator: linkUrlValidator,
      value: link,
    },
    {
      name: 'unitPrice',
      label: 'Unit Price',
      placeholder: '0.00625',
      validator: priceValidator,
      value: unitPrice?.toString(),
    },
  ];

  const handleSubmit: SubmitAction = ({
    name, description, image, link,
  }, setError) => {
    const args = {
      name: name!,
      description: description!,
      image: image!,
      link: link!,
    };

    const action = dataset ? () => updateDataset(id!, args) : () => addDataset(args);

    action()
      .then(() => navigate('/datadashboard'))
      .catch(setError);
  };

  return (
    <Container nav color={theme.colors.primary} justify="center" fullscreen>
      <Form onSubmit={handleSubmit} fields={fields} action="Sign In">
        <div>
          <h2>Add a Dataset</h2>
        </div>
        <p>
          Return to
          {' '}
          <a href="/datadashboard">Dashboard</a>
        </p>
      </Form>
    </Container>
  );
};

export default DatasetForm;
