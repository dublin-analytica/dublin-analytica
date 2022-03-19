import Status from './Status';

type AuthDTO = { email: string, password: string };
type RegistrationDTO = AuthDTO & { name: string };
type UpdateDTO = { name?: string, email?: string };
type UpdatePasswordDTO = { oldPassword: string, newPassword: string };
type OrderDTO = { status: Status };

type DTO = AuthDTO | RegistrationDTO | OrderDTO | UpdateDTO | UpdatePasswordDTO;

export default DTO;
