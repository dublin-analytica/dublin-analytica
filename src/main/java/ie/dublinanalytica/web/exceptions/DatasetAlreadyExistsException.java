package ie.dublinanalytica.web.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when trying to create a dataset that already exists.
 */
public class DatasetAlreadyExistsException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Dataset with this name already exists";
  public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.CONFLICT;

  public DatasetAlreadyExistsException() {
    super(DEFAULT_MESSAGE, DEFAULT_HTTP_STATUS);
  }
}
