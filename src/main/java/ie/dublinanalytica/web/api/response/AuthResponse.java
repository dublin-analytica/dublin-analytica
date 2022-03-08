package ie.dublinanalytica.web.api.response;

/**
 * Response returned when a user is authenticated.
 */
public class AuthResponse extends Response {
  private String jwt;

  public AuthResponse(String token) {
    super(null);
    this.jwt = token;
  }

  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }
}
