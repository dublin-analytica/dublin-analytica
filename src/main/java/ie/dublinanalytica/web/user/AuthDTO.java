package ie.dublinanalytica.web.user;

import java.util.Arrays;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ie.dublinanalytica.web.exceptions.PasswordNotStrongException;

/**
 * DTO for sending authentication request.
 */
public class AuthDTO {

  @NotNull
  @NotEmpty
  private String email;

  @NotNull
  @NotEmpty
  private char[] password;

  public AuthDTO(String email, char[] password) throws PasswordNotStrongException {
    this.email = email;
    this.password = verifyPasswordIsStrong(password);
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public char[] getPassword() {
    return password;
  }

  public void setPassword(char[] password) {
    this.password = password;
  }


}
