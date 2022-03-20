import Status from './Status';

type AuthDTO = { email: string, password: string };
type RegistrationDTO = AuthDTO & { name: string };
type UpdateDTO = { name?: string, email?: string };
type UpdatePasswordDTO = { oldPassword: string, newPassword: string };
type OrderDTO = { status: Status };
type BasketDTO = { id: string, size: number };
type CardDTO = { cvv: string, number: string, expiry: string };
export type DatasetDTO = {
  name: string, description: string, image: string, link: string, unitPrice: number
}

type DTO = AuthDTO
  | RegistrationDTO
  | OrderDTO
  | UpdateDTO
  | UpdatePasswordDTO
  | BasketDTO
  | CardDTO
  | DatasetDTO;

export default DTO;
