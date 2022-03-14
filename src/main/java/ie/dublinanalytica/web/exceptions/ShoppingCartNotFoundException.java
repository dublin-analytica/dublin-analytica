package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown if shopping cart not found.
 */
public class ShoppingCartNotFoundException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Shopping cart not found";

  public ShoppingCartNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
