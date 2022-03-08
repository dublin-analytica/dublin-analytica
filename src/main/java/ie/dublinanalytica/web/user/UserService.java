package ie.dublinanalytica.web.user;

import java.io.Serial;
import java.util.Optional;

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
      throw new UserAlreadyExistsException("User with email "
        + data.getEmail() + " already exists");
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

  public Optional<User> findById(long id) {
    return repository.findById(id);
  }

  public User findByEmail(String email) {
    return this.repository.findByEmail(email);
  }

  /**
   * RuntimeException to specify that a User already exists.
   */
  public static class UserAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String message) {
      super(message);
    }
  }
}
