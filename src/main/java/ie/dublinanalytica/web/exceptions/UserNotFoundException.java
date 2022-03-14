package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to query a user that does not exist.
 */
public class UserNotFoundException extends BaseException {
  public static final String DEFAULT_MESSAGE = "User couldn't be found";

  public UserNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
