package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to create a user that already exists.
 */
public class UserAlreadyExistsException extends BaseException {
  public static final String DEFAULT_MESSAGE = "User already exists";

  public UserAlreadyExistsException() {
    super(DEFAULT_MESSAGE);
  }
}
