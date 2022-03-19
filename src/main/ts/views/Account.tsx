import {
  SmartForm as Form, Sidebar, SplitView, Divider,
} from '@components';
import { SubmitAction } from '@components/SmartForm';
import { Container } from '@containers';
import { useAuth } from '@context/AuthProvider';
import { useAuthActions } from '@hooks';

import {
  nameValidator, emailValidator, passwordValidator, confirmationValidator,
} from '@utils/validators';

const Account = () => {
  const { user } = useAuth();
  const { update, changePassword } = useAuthActions();

  const updateDetailsFields = [
    {
      name: 'name', label: 'Name', validator: nameValidator, value: user?.name,
    },
    {
      name: 'email', label: 'Email', validator: emailValidator, value: user?.email,
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

  const updateDetails: SubmitAction = ({ name, email }, setError) => {
    update(name!, email!).catch(setError);
  };

  const updatePassword: SubmitAction = ({ oldPassword, password }, setError) => {
    changePassword(oldPassword!, password!).catch(setError);
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
