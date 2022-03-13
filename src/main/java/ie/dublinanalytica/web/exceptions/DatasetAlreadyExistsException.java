package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to create a dataset that already exists.
 */
public class DatasetAlreadyExistsException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Dataset already exists";

  public DatasetAlreadyExistsException() {
    super(DEFAULT_MESSAGE);
  }
}
