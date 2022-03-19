import Status from './Status';

type AuthDTO = { email: string, password: string };
type RegistrationDTO = AuthDTO & { name: string };
type OrderDTO = { status: Status };

type DTO = AuthDTO | RegistrationDTO | OrderDTO;

export default DTO;
