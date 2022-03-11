package ie.dublinanalytica.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Base exception class for all exceptions in the application.
 * Contains the status code to be used in the response. BAD_REQUEST by default
 */
public class BaseException extends Exception {
  private final HttpStatus status;

  public BaseException(String message, HttpStatus httpStatus) {
    super(message);
    this.status = httpStatus;
  }

  public BaseException(String message) {
    this(message, HttpStatus.BAD_REQUEST);
  }

  public HttpStatus getStatus() {
    return this.status;
  }
}
