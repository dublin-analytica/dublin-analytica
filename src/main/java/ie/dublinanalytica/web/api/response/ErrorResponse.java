package ie.dublinanalytica.web.api.response;

/**
 * An error response of the API.
 */
public class ErrorResponse extends Response {
  private String name;
  private String message;

  /**
   * Creates an error response with the given name and message.
   *
   * @param name The name
   * @param message The message
   */
  public ErrorResponse(String name, String message) {
    super(null);
    this.name = name;
    this.message = message;
  }

  public String getName() {
    return name;
  }

  public String setName(String name) {
    return this.name = name;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
