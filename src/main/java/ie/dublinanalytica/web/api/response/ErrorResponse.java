package ie.dublinanalytica.web.api.response;

import org.springframework.http.HttpStatus;

import ie.dublinanalytica.web.exceptions.BaseException;

/**
 * An error response containing the error name, message and status code.
 */
public class ErrorResponse extends Response {

  public ErrorResponse(String name, String message, HttpStatus status) {
    super(new ErrorObject(name, message), status);
  }

  public ErrorResponse(BaseException e, HttpStatus status) {
    super(new ErrorObject(e), status);
  }

  public ErrorResponse(BaseException e) {
    super(new ErrorObject(e), e.getStatus());
  }

  /**
   * The json object returned.
   */
  public record ErrorObject(String name, String message) {
    public ErrorObject(BaseException e) {
      this(e.getClass().getSimpleName(), e.getMessage());
    }

    public String getName() {
      return name;
    }

    public String getMessage() {
      return message;
    }
  }
}
