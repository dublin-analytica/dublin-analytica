package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to add an item that already exists.
 */
public class ItemAlreadyExistsException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Item already exists";

  public ItemAlreadyExistsException() {
    super(DEFAULT_MESSAGE);
  }
}
