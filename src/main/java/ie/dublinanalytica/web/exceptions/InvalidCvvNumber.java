package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to use a card with an invalid cvv number.
 */
public class InvalidCvvNumber extends BaseException {
  public static final String DEFAULT_MESSAGE = "Invalid cvv number";

  public InvalidCvvNumber() {
    super(DEFAULT_MESSAGE);
  }
}
