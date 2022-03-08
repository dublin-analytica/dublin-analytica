package ie.dublinanalytica.web.user;

import ie.dublinanalytica.web.util.Authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represents a User (customer) of the website.
 */
@Entity
@JsonIgnoreProperties(value = {"authHash", "salt", "authTokens"})
public class User {

  @Id
  @GeneratedValue
  private long id;

  private String name;
  private String email;

  @ElementCollection
  private Set<String> authTokens;
  private byte[] authHash;
  private byte[] salt;

  public User() {
  }

  /**
   * Creates a User from registration DTO.
   *
   * @param data The registration DTO
   */
  public User(RegistrationDTO data) {
    this(data.getName(), data.getEmail(), data.getPassword());
  }

  /**
   * Creates a User.
   *
   * @param name The name of the user.
   * @param email The email of the user
   * @param authHash A hash of the password and salt using 'PBKDF2WithHmacSHA512'
   * @param salt The salt used for hashing the password
   */
  public User(String name, String email, byte[] authHash, byte[] salt) {
    this.name = name;
    this.email = email;
    this.authHash = authHash;
    this.salt = salt;
    this.authTokens = new HashSet<>();
  }

  /**
   * Creates a User.
   *
   * @param name The name of the User
   * @param email The email of the User
   * @param password The password of the User. This will not be stored but used to
   *                 create the authHash using a random salt.
   */
  public User(String name, String email, char[] password) {
    this.name = name;
    this.email = email;
    this.salt = Authentication.generateSalt();
    this.authHash = Authentication.hash(password, this.salt);
    Arrays.fill(password, '\0');
    this.authTokens = new HashSet<>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public byte[] getAuthHash() {
    return authHash;
  }

  public void setAuthHash(byte[] authHash) {
    this.authHash = authHash;
  }

  public byte[] getSalt() {
    return salt;
  }

  public void setSalt(byte[] salt) {
    this.salt = salt;
  }

  public boolean verifyPassword(char[] password) {
    return Authentication.verifyPassword(password, this.salt, this.authHash);
  }

  public void addAuthToken(String authToken) {
    this.authTokens.add(authToken);
  }

  public void removeAuthToken(String authToken) {
    this.authTokens.remove(authToken);
  }

  public void removeAllAuthTokens() {
    this.authTokens.clear();
  }
}
