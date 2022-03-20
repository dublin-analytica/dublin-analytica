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
    this.password = password;
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
