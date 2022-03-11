package ie.dublinanalytica.web.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ie.dublinanalytica.web.exceptions.UserAlreadyExistsException;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.exceptions.UserNotFoundException;
import ie.dublinanalytica.web.exceptions.WrongPasswordException;
import ie.dublinanalytica.web.util.AuthUtils;

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
  private boolean userExists(String email) {
    return this.repository.findByEmail(email) != null;
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

    repository.save(new User(data));
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
    repository.save(user);
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
    repository.save(user);
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
    repository.save(user);
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
    Optional<User> user = this.repository.findById(id);

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
    User user = this.repository.findByEmail(email);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return this.repository.findByEmail(email);
  }
}
