package ie.dublinanalytica.web.api;

import java.util.HashMap;
import java.util.UUID;

import com.auth0.jwt.interfaces.DecodedJWT;

import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.util.AuthUtils;

/**
 * The payload of the JWT used.
 */
public class JWTPayload {
  private UUID id;
  private String name;
  private String email;
  private String authToken;
  private boolean admin;

  /**
   * Create the JWT token from the information.
   *
   * @param id The user's id
   * @param name The user's name
   * @param email The user's email
   * @param token The user's auth token
   */
  public JWTPayload(UUID id, String name, String email, String token, boolean admin) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.authToken = token;
    this.admin = admin;
  }

  public JWTPayload(User user, String token) {
    this(user.getId(), user.getName(), user.getEmail(), token, user.isAdmin());
  }

  /**
   * Creates a JWTPayload from the raw Authorization request header.
   *
   * @param authHeader The Authorization header
   * @throws UserAuthenticationException If the header or JWT is invalid
   */
  public static JWTPayload fromHeader(String authHeader) throws UserAuthenticationException {
    DecodedJWT jwt = AuthUtils.decodeJWTFromHeader(authHeader);

    String uuidString = jwt.getClaim("id").asString();
    String name = jwt.getClaim("name").asString();
    String email = jwt.getClaim("email").asString();
    String authToken = jwt.getClaim("authToken").asString();
    boolean admin = jwt.getClaim("admin").asBoolean();

    if (uuidString == null || name == null || email == null || authToken == null) {
      throw new UserAuthenticationException("Invalid JWT payload");
    }

    return new JWTPayload(UUID.fromString(uuidString), name, email, authToken, admin);
  }

  /**
   * Creates the JWT string token from the payload.
   *
   * @return The JWT string token
   */
  public String encode() {
    return AuthUtils.createJWT(new HashMap<>() {
      {
        put("id", id.toString());
        put("name", name);
        put("email", email);
        put("authToken", authToken);
        put("admin", admin);
      }
    });
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setId(String id) {
    this.id = UUID.fromString(id);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public boolean isAdmin() {
    return admin;
  }
}
