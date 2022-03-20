package ie.dublinanalytica.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when password is not strong.
 */
public class PasswordNotStrongException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Order with that ID couldn't be found";
  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.NOT_FOUND;

  public PasswordNotStrongException() {
    super(DEFAULT_MESSAGE, DEFAULT_HTTP_STATUS);
  }
}
