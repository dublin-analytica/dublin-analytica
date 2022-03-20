package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when password is not strong.
 */
public class PasswordNotStrongException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Password is not strong";

  public PasswordNotStrongException() {
    super(DEFAULT_MESSAGE);
  }
}
