package ie.dublinanalytica.web.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response returned when a user is authenticated.
 */
public class AuthResponse extends Response {
  private String jwt;

  public AuthResponse(@JsonProperty("jwt") String token) {
    super(null);
    this.jwt = token;
  }

  public String getJWT() {
    return jwt;
  }

  public void setJWT(String jwt) {
    this.jwt = jwt;
  }
}
