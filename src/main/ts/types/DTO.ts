type AuthDTO = { email: string, password: string };
type RegistrationDTO = AuthDTO & { name: string };

type DTO = AuthDTO | RegistrationDTO;

export default DTO;
