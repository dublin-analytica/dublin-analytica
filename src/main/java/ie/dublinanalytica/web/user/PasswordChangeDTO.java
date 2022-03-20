package ie.dublinanalytica.web.user;

import ie.dublinanalytica.web.exceptions.PasswordNotStrongException;

import java.util.Arrays;

/**
 * DTO for password change.
 */
public class PasswordChangeDTO {

  public char[] oldPassword;
  public char[] newPassword;

  public PasswordChangeDTO() {
  }

  /**
   * If password is not strong, then an exception is thrown. 
   * If password is strong, then we return the password.
   *
   * @param password The password.
   * @return The password.
   * @throws PasswordNotStrongException if the password is not strong.
   */
  public char[] verifyPasswordIsStrong(char[] password) throws PasswordNotStrongException {
    if (Arrays.toString(password)
        .matches(("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?=.{8,})"))) {
      return password;
    } else {
      throw new PasswordNotStrongException();
    }
  }

  public void setOldPassword(char[] oldPassword) {
    this.oldPassword = oldPassword;
  }

  public char[] getOldPassword() {
    return oldPassword;
  }

  public void setNewPassword(char[] newPassword) throws PasswordNotStrongException {
    this.newPassword = verifyPasswordIsStrong(newPassword);
  }

  public char[] getNewPassword() {
    return newPassword;
  }
}
