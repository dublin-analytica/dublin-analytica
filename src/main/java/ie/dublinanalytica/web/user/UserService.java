package ie.dublinanalytica.web.user;

import java.io.Serial;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A service class to handle User related operations.
 */
@Service
public class UserService {

  private UserRepository repository;

  @Autowired
  public void setUserRepository(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * Checks whether a User with a given email exists.
   *
   * @param email The email to check
   * @return true if a User with the given email exists, false otherwise
   */
  public boolean userExists(String email) {
    return this.repository.findByEmail(email) != null;
  }

  /**
   * Creates a new User.
   *
   * @param data The DTO containing the User data required for registration
   */
  public void registerUser(RegistrationDTO data) {
    if (userExists(data.getEmail())) {
      throw new UserAlreadyExistsException(
        String.format("User with email '%s' already exist", data.getEmail()));
    }

    repository.save(new User(data));
  }

  /**
   * Adds an authentication token to a User.
   *
   * @param user The user to add the token to
   * @param token The token to add
   */
  public void addAuthToken(User user, String token) {
    user.addAuthToken(token);
    repository.save(user);
  }

  /**
   * Removes an authentication token from a User.
   *
   * @param user The user
   * @param token The auth token to remove
   */
  public void removeAuthToken(User user, String token) {
    user.removeAuthToken(token);
    repository.save(user);
  }

  /**
   * Removes all auth tokens from a user.
   *
   * @param user the user from which to do so
   */
  public void removeAllAuthTokens(User user) {
    user.removeAllAuthTokens();
    repository.save(user);
  }

  /**
   * Verifies that a user has a given auth token.
   *
   * @param user The user
   * @param token The auth token to check
   * @throws UserAuthenticationException if the auth token does not belong to the user
   */
  public void verifyAuthToken(User user, String token) {
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
  public User findById(UUID id) {
    Optional<User> user = this.repository.findById(id);

    if (user.isEmpty()) {
      throw new UserNotFoundException("User with id " + id + " not found");
    }

    return user.get();
  }

  public User findByEmail(String email) {
    return this.repository.findByEmail(email);
  }

  /**
   * Wrapper for all User exceptions.
   */
  public abstract static class BaseUserException extends RuntimeException {
    public BaseUserException(String message) {
      super(message);
    }

    public String getName() {
      return this.getClass().getSimpleName();
    }
  }

  /**
   * RuntimeException to specify that a User already exists.
   */
  public static class UserAlreadyExistsException extends BaseUserException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String message) {
      super(message);
    }
  }

  /**
   * RuntimeException to specify that a User does not exist.
   */
  public static class UserNotFoundException extends BaseUserException {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String defaultMessage = "User not found";

    public UserNotFoundException(String message) {
      super(message);
    }

    public UserNotFoundException() {
      super(defaultMessage);
    }
  }

  /**
   * RuntimeException to specify that a User could not be authenticated.
   */
  public static class UserAuthenticationException extends BaseUserException {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String defaultMessage = "Auth token is invalid";

    public UserAuthenticationException(String message) {
      super(message);
    }

    public UserAuthenticationException() {
      super(defaultMessage);
    }
  }

  /**
   * JWT Exception errors.
   */
  public static class JWTException extends BaseUserException {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String defaultMessage = "JWT error";

    public JWTException(String message) {
      super(message);
    }

    public JWTException() {
      super(defaultMessage);
    }
  }
}
