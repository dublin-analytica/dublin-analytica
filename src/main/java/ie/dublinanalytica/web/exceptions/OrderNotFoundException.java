package ie.dublinanalytica.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when trying to query an order that does not exist.
 */
public class OrderNotFoundException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Order with that ID couldn't be found";
  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.NOT_FOUND;

  public OrderNotFoundException() {
    super(DEFAULT_MESSAGE, DEFAULT_HTTP_STATUS);
  }
}
