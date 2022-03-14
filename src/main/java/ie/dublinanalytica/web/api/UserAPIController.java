package ie.dublinanalytica.web.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.api.response.EmptyResponse;
import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.exceptions.UserAlreadyExistsException;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.exceptions.UserNotFoundException;
import ie.dublinanalytica.web.exceptions.WrongPasswordException;
import ie.dublinanalytica.web.user.AuthDTO;
import ie.dublinanalytica.web.user.RegistrationDTO;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;

/**
 * API Controller for /api/user endpoints.
 */
@RestController
@RequestMapping("/api/users")
public class UserAPIController {

  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  /**
   * Performs login for a user. Returns a JWT token if successful.
   *
   * @param data Auth information
   * @return A JSON object containing the information about the user and an auth token
   * @throws UserNotFoundException if the user trying to log in couldn't be found
   * @throws WrongPasswordException if the password is incorrect
   */
  @PostMapping("/login")
  public Response login(@RequestBody AuthDTO data)
      throws UserNotFoundException, WrongPasswordException {
    User user = userService.findByEmail(data.getEmail());
    String authToken = userService.createNewAuthToken(user, data.getPassword());
    return new AuthResponse(user, authToken);
  }

  /**
   * Performs logout for a user by revoking their auth token.
   *
   * @param authHeader The Authorization header
   * @return 200 OK on success, error message on failure
   * @throws UserAuthenticationException if no authorization header is provided or is invalid
   * @throws UserNotFoundException if the user trying to log out wasn't found
   */
  @PostMapping("/logout")
  public Response login(@RequestHeader("Authorization") String authHeader)
      throws UserAuthenticationException, UserNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    userService.removeAuthToken(user, payload.getAuthToken());
    return new EmptyResponse(HttpStatus.OK);
  }

  /**
   * Registers a new user.
   *
   * @param data Registration information
   * @return Nothing on success, an error message on failure
   * @throws UserAlreadyExistsException if the user already exists
   */
  @PostMapping("/register")
  public Response register(@RequestBody @Valid RegistrationDTO data)
      throws UserAlreadyExistsException {
    userService.registerUser(data);
    return new EmptyResponse(HttpStatus.CREATED);
  }

  /**
   * Gets the current user's information.
   * Reads the JWT Token from the Authorization header.
   *
   * @param authHeader Authorization header.
   * @return The user's information if the token is valid, an error message otherwise
   * @throws UserAuthenticationException if no authorization header is provided or is invalid
   * @throws UserNotFoundException if the user wasn't found
   */
  @GetMapping("/me")
  public Response me(@RequestHeader("Authorization") String authHeader)
      throws UserAuthenticationException, UserNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    userService.verifyAuthToken(user, payload.getAuthToken());
    return new Response(user);
  }
}
