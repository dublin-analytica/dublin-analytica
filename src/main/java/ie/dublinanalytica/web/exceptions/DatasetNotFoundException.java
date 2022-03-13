package ie.dublinanalytica.web.exceptions;

/**
 * Exception thrown when trying to query a user that does not exist.
 */
public class DatasetNotFoundException extends BaseException {
  public static final String DEFAULT_MESSAGE = "Dataset couldn't be found";

  public DatasetNotFoundException() {
    super(DEFAULT_MESSAGE);
  }

}
