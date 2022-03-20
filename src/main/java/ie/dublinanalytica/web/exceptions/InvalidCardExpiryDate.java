package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to use a card with an invalid card expiry date.
 */
public class InvalidCardExpiryDate extends BaseException {
  public static final String DEFAULT_MESSAGE = "Invalid card expiry date";

  public InvalidCardExpiryDate() {
    super(DEFAULT_MESSAGE);
  }

  public InvalidCardExpiryDate(String message) {
    super(message);
  }
}
