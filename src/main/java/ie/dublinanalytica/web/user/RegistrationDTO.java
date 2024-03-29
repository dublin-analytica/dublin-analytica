package ie.dublinanalytica.web.user;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ie.dublinanalytica.web.exceptions.PasswordNotStrongException;

/**
 * Data-Transfer-Object to store the necessary information for a user to register.
 */
public class RegistrationDTO {

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  @NotEmpty
  private String email;

  @NotNull
  @NotEmpty
  private char[] password;

  /**
   * Create RegistrationDTO for testing.
   *
   * @param name The name
   * @param email The email
   * @param password The password
   */
  public RegistrationDTO(String name, String email, char[] password)
      throws PasswordNotStrongException {
    this.name = name;
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
    if (Pattern.matches(
        ("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!*@#$%^&+=]).{8,}$"),
        CharBuffer.wrap(password))) {
      return password;
    } else {
      throw new PasswordNotStrongException();
    }
  }

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
    return String.format(
      "RegistrationDTO{name: '%s', email: '%s', password: '%s'}",
      name, email, Arrays.toString(password));
  }

  public void clear() {
    Arrays.fill(password, '\0');
  }
}
