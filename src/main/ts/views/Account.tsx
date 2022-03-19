import {
  SmartForm as Form, Sidebar, SplitView, Divider,
} from '@components';
import { Container } from '@containers';
import { useAuth } from '@context/AuthProvider';

import {
  nameValidator, emailValidator, passwordValidator, confirmationValidator,
} from '@utils/validators';

const Account = () => {
  const { user } = useAuth();

  const updateDetailsFields = [
    {
      name: 'name', label: 'Name', placeholder: user?.name, validator: nameValidator,
    },
    {
      name: 'email', label: 'Email', placeholder: user?.email, validator: emailValidator,
    },
  ];

  const updatePasswordFields = [
    { name: 'oldPassword', label: 'Old Password', type: 'password' },
    {
      name: 'password', label: 'New Password', type: 'password', validator: passwordValidator,
    },
    {
      name: 'confirmation', label: 'Confirm New Password', type: 'password', validator: confirmationValidator,
    },
  ];

  const updateDetails = () => {

  };

  const updatePassword = () => {
  };

  return (
    <SplitView>
      <Sidebar />
      <Container direction="row" justify="space-around">
        <Form onSubmit={updateDetails} fields={updateDetailsFields} action="Update Details" />
        <Divider />
        <Form onSubmit={updatePassword} fields={updatePasswordFields} action="Update Password" />
      </Container>
    </SplitView>
  );
};

export default Account;
