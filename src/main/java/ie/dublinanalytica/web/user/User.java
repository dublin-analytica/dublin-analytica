package ie.dublinanalytica.web.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import ie.dublinanalytica.web.shoppingcart.ItemDTO;
import ie.dublinanalytica.web.shoppingcart.ShoppingCart;
import ie.dublinanalytica.web.util.AuthUtils;

/**
 * Represents a User (customer) of the website.
 */
@Entity
@JsonIgnoreProperties(value = {"authHash", "salt", "authTokens", "cart"})
public class User extends BaseUser {

  private ShoppingCart cart;

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
    super(name, email);
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
    super(name, email);
    this.salt = AuthUtils.generateSalt();
    this.authHash = AuthUtils.hash(password, this.salt);
    Arrays.fill(password, '\0');
    this.authTokens = new HashSet<>();
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
    return AuthUtils.verifyPassword(password, this.salt, this.authHash);
  }

  public void addAuthToken(String authToken) {
    this.authTokens.add(authToken);
  }

  public boolean verifyAuthToken(String authToken) {
    return this.authTokens.contains(authToken);
  }

  public void removeAuthToken(String authToken) {
    this.authTokens.remove(authToken);
  }

  public void removeAllAuthTokens() {
    this.authTokens.clear();
  }

  public ShoppingCart getCart() {
    return this.cart;
  }

  public void addToCart(ItemDTO item) {
    this.cart.addItem(item);
  }
}
