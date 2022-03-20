import { useCartActions } from '@hooks';
import { cardCvvValidator, cardExpiryValidator, cardNumberValidator } from '@utils/validators';
import { useNavigate } from 'react-router-dom';
import Form, { SubmitAction } from './SmartForm';

const Checkout = () => {
  const navigate = useNavigate();
  const { checkout } = useCartActions();

  const fields = [
    {
      name: 'cardNumber', label: 'Card Number', type: 'text', placeholder: '1234 5678 9012 3456', validator: cardNumberValidator,
    },
    {
      name: 'expiry', label: 'Expiry Date', type: 'text', placeholder: 'MM/YY', validator: cardExpiryValidator,
    },
    {
      name: 'cvv', label: 'CVV', type: 'text', placeholder: '123', validator: cardCvvValidator,
    },
  ];

  const handleSubmit: SubmitAction = ({ cardNumber, expiry, cvv }, setError) => {
    const number = cardNumber?.replace(/[\s-]/g, '');
    checkout(number!, expiry!, cvv!)
      .then(() => navigate('/orders'))
      .catch(setError);
  };

  return (
    <Form fields={fields} action="Complete Purchase" onSubmit={handleSubmit} validated />
  );
};

export default Checkout;
