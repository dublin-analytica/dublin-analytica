package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to query an order that does not exist.
 */
public class OrderNotFoundException extends Exception {
  public static final String DEFAULT_MESSAGE = "Order couldn't be found";

  public OrderNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
