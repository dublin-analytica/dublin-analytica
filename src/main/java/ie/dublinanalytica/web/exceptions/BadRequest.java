package ie.dublinanalytica.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Bad request exception for when no better exception exists.
 */
public class BadRequest extends BaseException {
  public BadRequest(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
