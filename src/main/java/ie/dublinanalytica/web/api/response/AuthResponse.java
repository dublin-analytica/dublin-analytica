package ie.dublinanalytica.web.api.response;

import org.springframework.http.HttpStatus;


import ie.dublinanalytica.web.api.JWTPayload;
import ie.dublinanalytica.web.user.User;

/**
 * Response returned when a user is authenticated.
 */
public class AuthResponse extends Response {

  public AuthResponse(User user, String token) {
    super(new AuthObject(user, token), HttpStatus.OK);
  }

  /**
   * The json object returned.
   */
  public record AuthObject(String token) {
    public AuthObject(JWTPayload payload) {
      this(payload.encode());
    }

    public AuthObject(User user, String token) {
      this(new JWTPayload(user, token));
    }
  }
}
