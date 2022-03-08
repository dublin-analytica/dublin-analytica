package ie.dublinanalytica.web.api.response;

/**
 * An error response of the API.
 */
public class ErrorResponse extends Response {
  private String error;

  public ErrorResponse(String message) {
    super(null);
    this.error = message;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
