package ie.dublinanalytica.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user is not authenticated to perform an action.
 */
public class UserAuthenticationException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Invalid authentication credentials";
  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.UNAUTHORIZED;

  public UserAuthenticationException(String message, HttpStatus status) {
    super(message, status);
  }

  public UserAuthenticationException(String message) {
    this(message, DEFAULT_HTTP_STATUS);
  }

  public UserAuthenticationException() {
    super(DEFAULT_MESSAGE, DEFAULT_HTTP_STATUS);
  }
}
