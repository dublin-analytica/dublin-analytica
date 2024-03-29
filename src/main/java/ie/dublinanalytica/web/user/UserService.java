package ie.dublinanalytica.web.user;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ie.dublinanalytica.web.dataset.Dataset;
import ie.dublinanalytica.web.dataset.DatasetService;
import ie.dublinanalytica.web.exceptions.BadRequest;
import ie.dublinanalytica.web.exceptions.DatasetNotFoundException;
import ie.dublinanalytica.web.exceptions.OrderNotFoundException;
import ie.dublinanalytica.web.exceptions.UserAlreadyExistsException;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.exceptions.UserNotFoundException;
import ie.dublinanalytica.web.exceptions.WrongPasswordException;
import ie.dublinanalytica.web.orders.Order;
import ie.dublinanalytica.web.orders.OrderService;
import ie.dublinanalytica.web.shoppingcart.ItemDTO;
import ie.dublinanalytica.web.shoppingcart.ShoppingCart;
import ie.dublinanalytica.web.util.AuthUtils;

/**
 * A service class to handle User related operations.
 */
@Service
public class UserService {

  private UserRepository userRepository;
  private DatasetService datasetService;
  private OrderService orderService;

  @Autowired
  public void setUserRepository(UserRepository repository) {
    this.userRepository = repository;
  }

  @Autowired
  public void setDatasetService(DatasetService service) {
    this.datasetService = service;
  }

  @Autowired
  public void setOrderService(@Lazy OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Checks whether a User with a given email exists.
   *
   * @param email The email to check
   * @return true if a User with the given email exists, false otherwise
   */
  private boolean userExists(String email) {
    return this.userRepository.findByEmail(email) != null;
  }

  /**
   * Creates a new User.
   *
   * @param data The DTO containing the User data required for registration
   * @throws UserAlreadyExistsException if a user with the given email already exists
   */
  public void registerUser(RegistrationDTO data) throws UserAlreadyExistsException {
    if (userExists(data.getEmail())) {
      throw new UserAlreadyExistsException();
    }

    userRepository.save(new User(data));
  }

  public Map<UUID, Integer> getCart(User user, String token) throws UserAuthenticationException {
    verifyAuthToken(user, token);
    return user.getCart().getItems();
  }

  /**
   * Gets a users order.
   *
   * @param orderId the order id
   * @throws OrderNotFoundException if order not found
   */
  public Order getOrder(UUID orderId) throws OrderNotFoundException {
    return orderService.findById(orderId);
  }


  /**
   * Adds an item to a user's cart.
   *
   * @param user The user
   * @param token Their JWT token
   * @param item The item to add
   * @throws UserAuthenticationException if the user isn't authenticated
   */
  public void addToCart(User user, String token, ItemDTO item)
      throws UserAuthenticationException, DatasetNotFoundException, BadRequest {
    verifyAuthToken(user, token);

    UUID datasetId = item.getId();
    Dataset dataset = datasetService.findById(datasetId);

    if (dataset.isHidden()) {
      throw new BadRequest("Cannot add hidden datasets to cart");
    }

    int count = item.getSize();

    if (count <= 0) {
      throw new BadRequest("Can't add less than 1 item to cart");
    }

    Map<UUID, Integer> items = user.getCart().getItems();

    int current = items.getOrDefault(datasetId, 0);
    int updated = current + count;

    user.getCart().put(datasetId, Math.min(updated, dataset.getSize()));

    userRepository.save(user);
  }

  /**
   * Sets a cart item for a dataset to a given count.
   *
   * @param user The User
   * @param authToken Their JWT token
   * @param item The item information
   * @throws DatasetNotFoundException If the dataset doesn't exist
   * @throws BadRequest If the dataset doesn't have enough datapoints, or the requested count is 0
   * @throws UserAuthenticationException If the user isn't authenticated
   */
  public void updateCart(User user, String authToken, ItemDTO item)
      throws DatasetNotFoundException, BadRequest, UserAuthenticationException {
    verifyAuthToken(user, authToken);

    UUID datasetId = item.getId();
    Dataset dataset = datasetService.findById(datasetId);

    if (dataset.isHidden()) {
      throw new BadRequest("Cannot add hidden datasets to cart");
    }

    if (item.getSize() < 0) {
      throw new BadRequest("Can't have less than 0 datapoints");
    }

    if (item.getSize() > dataset.getSize()) {
      throw new BadRequest("Dataset does not have enough datapoints");
    }

    ShoppingCart cart = user.getCart();
    cart.put(datasetId, item.getSize());
    userRepository.save(user);
  }

  /**
   * Clears a users cart.
   *
   * @param user The user
   * @param authToken Their JWT token
   * @throws UserAuthenticationException If the user isn't authenticated
   */
  public void clearCart(User user, String authToken) throws UserAuthenticationException {
    verifyAuthToken(user, authToken);
    user.getCart().clear();
    userRepository.save(user);
  }

  /**
   * Removes an authentication token from a User.
   *
   * @param user The user
   * @param token The auth token to remove
   * @throws UserAuthenticationException if the auth token does not belong to the user
   */
  public void removeAuthToken(User user, String token) throws UserAuthenticationException {
    verifyAuthToken(user, token);
    user.removeAuthToken(token);
    userRepository.save(user);
  }

  /**
   * Removes all auth tokens from a user.
   *
   * @param user the user from which to do so
   * @throws UserAuthenticationException if the provided token doesn't belong to the user
   */
  public void removeAllAuthTokens(User user, String token) throws UserAuthenticationException {
    verifyAuthToken(user, token);
    user.removeAllAuthTokens();
    userRepository.save(user);
  }

  /**
   * Verifies a user's password.
   *
   * @param user The user
   * @param password The password
   * @throws WrongPasswordException If the password doesn't match the stored hash
   */
  private void verifyPassword(User user, char[] password) throws WrongPasswordException {
    if (!user.verifyPassword(password)) {
      throw new WrongPasswordException();
    }
  }

  /**
   * Creates a new auth token for a user.
   *
   * @param user The user
   * @param password Their password
   * @return The new auth token
   * @throws WrongPasswordException If their password doesn't match the stored hash
   */
  public String createNewAuthToken(User user, char[] password) throws WrongPasswordException {
    verifyPassword(user, password);
    String token = AuthUtils.generateAuthToken();
    user.addAuthToken(token);
    userRepository.save(user);
    return token;
  }

  /**
   * Verifies that a user has a given auth token.
   *
   * @param user The user
   * @param token The auth token to check
   * @throws UserAuthenticationException if the auth token does not belong to the user
   */
  public void verifyAuthToken(User user, String token) throws UserAuthenticationException {
    if (!user.verifyAuthToken(token)) {
      throw new UserAuthenticationException();
    }
  }

  /**
   * Finds a user by their UUID.
   *
   * @param id The UUID of the user to find
   * @return The User object
   * @throws UserNotFoundException if the user with that ID is not found
   */
  public User findById(UUID id) throws UserNotFoundException {
    Optional<User> user = this.userRepository.findById(id);

    if (user.isEmpty()) {
      throw new UserNotFoundException();
    }

    return user.get();
  }

  /**
   * Finds a user by their email.
   *
   * @param email The user's email
   * @return The User object
   * @throws UserNotFoundException If the user with the given email doesn't exist
   */
  public User findByEmail(String email) throws UserNotFoundException {
    User user = this.userRepository.findByEmail(email);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return this.userRepository.findByEmail(email);
  }

  /**
   * Checks that the provided user is an admin, throwing an exception if not.
   *
   * @param user The user to check
   */
  public void verifyAdmin(User user) throws UserAuthenticationException {
    if (!user.isAdmin()) {
      throw new UserAuthenticationException("User is not an admin", HttpStatus.FORBIDDEN);
    }
  }

  public void save(User user) {
    userRepository.save(user);
  }

  /**
   * Updates the user's password.
   *
   * @param user The user
   * @param oldPassword Their old password
   * @param newPassword Their new password
   * @throws WrongPasswordException If the old password doesn't match the stored hash
   */
  public void changePassword(User user, char[] oldPassword, char[] newPassword)
      throws WrongPasswordException {
    verifyPassword(user, oldPassword);
    user.setPassword(newPassword);
    userRepository.save(user);
  }
}
