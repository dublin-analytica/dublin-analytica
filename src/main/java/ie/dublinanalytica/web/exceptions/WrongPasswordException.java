package ie.dublinanalytica.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when trying to sign in with a wrong password.
 */
public class WrongPasswordException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Wrong password. Try again.";
  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.UNAUTHORIZED;

  public WrongPasswordException() {
    super(DEFAULT_MESSAGE, DEFAULT_HTTP_STATUS);
  }
}
