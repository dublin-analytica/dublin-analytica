package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to create a user that already exists.
 */
public class UserAlreadyExistsException extends BaseException {
  public static final String DEFAULT_MESSAGE = "An account with this email already exists.";

  public UserAlreadyExistsException() {
    super(DEFAULT_MESSAGE);
  }
}
