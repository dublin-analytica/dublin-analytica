package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to use a card with an invalid card number.
 */
public class InvalidCardNumber extends BaseException {
  public static final String DEFAULT_MESSAGE = "Invalid card number";

  public InvalidCardNumber() {
    super(DEFAULT_MESSAGE);
  }

  public InvalidCardNumber(String message) {
    super(message);
  }
}
