package ie.dublinanalytica.web.user;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class for storing basic user information.
 */
@Entity
public class BaseUser {

  @Id
  @GeneratedValue
  private UUID id;

  private String name;
  private String email;

  public BaseUser() {
  }

  public BaseUser(String name, String email) {
    this.name = name;
    this.email = email;
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

  public void setId(UUID id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Creates a BaseUser from a DecodedJWT.
   *
   * @param jwt the DecodedJWT containing the user's name, password and id
   * @return The BaseUser created from the DecodedJWT
   */
  public static BaseUser fromJWT(DecodedJWT jwt) {
    BaseUser user = new BaseUser(jwt.getClaim("name").asString(), jwt.getClaim("email").asString());
    user.setId(UUID.fromString(jwt.getClaim("id").asString()));

    return user;
  }
}
