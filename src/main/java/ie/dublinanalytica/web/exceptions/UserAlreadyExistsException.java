package ie.dublinanalytica.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when trying to create a user that already exists.
 */
public class UserAlreadyExistsException extends BaseException {
  public static final String DEFAULT_MESSAGE = "An account with this email already exists.";
  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.CONFLICT;

  public UserAlreadyExistsException() {
    super(DEFAULT_MESSAGE, DEFAULT_HTTP_STATUS);
  }
}
