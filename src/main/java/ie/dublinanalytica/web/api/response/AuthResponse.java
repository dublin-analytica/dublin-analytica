package ie.dublinanalytica.web.api.response;

/**
 * Response returned when a user is authenticated.
 */
public class AuthResponse extends Response {
  private String jwtToken;

  public AuthResponse(String token) {
    super(null);
    this.jwtToken = token;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }
}
