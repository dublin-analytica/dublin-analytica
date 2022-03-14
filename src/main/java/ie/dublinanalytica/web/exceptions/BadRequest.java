package ie.dublinanalytica.web.exceptions;

/**
 * Bad request exception for when no better exception exists.
 */
public class BadRequest extends BaseException {
  public BadRequest(String message) {
    super(message);
  }
}
