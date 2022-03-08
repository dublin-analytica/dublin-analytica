package ie.dublinanalytica.web.user;

import ie.dublinanalytica.web.util.ValidEmail;

import java.util.Arrays;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Data-Transfer-Object to store the necessary information for a user to register.
 */
public class RegistrationDTO {

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  @NotEmpty
  @ValidEmail
  private String email;

  @NotNull
  @NotEmpty
  private char[] password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  @Override
  public String toString() {
    return "RegistrationData [name="
      + name + ", email="
      + email + ", password="
      + Arrays.toString(password) + "]";
  }

  public void clear() {
    Arrays.fill(password, '\0');
  }
}
