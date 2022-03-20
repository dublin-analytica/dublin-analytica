package ie.dublinanalytica.web.user;

import ie.dublinanalytica.web.exceptions.PasswordNotStrongException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

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

  public AuthDTO(String email, char[] password) {
    this.email = email;
    this.password = verifyPasswordIsStrong(password);
  }

  public char[] verifyPasswordIsStrong(char[] password) {
    if (Arrays.toString(password).matches(("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?=.{8,})"))) {
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
