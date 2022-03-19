package ie.dublinanalytica.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when trying to query a user that does not exist.
 */
public class UserNotFoundException extends BaseException {
  public static final String DEFAULT_MESSAGE = "User couldn't be found";
  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.NOT_FOUND;

  public UserNotFoundException() {
    super(DEFAULT_MESSAGE, DEFAULT_HTTP_STATUS);
  }
}
