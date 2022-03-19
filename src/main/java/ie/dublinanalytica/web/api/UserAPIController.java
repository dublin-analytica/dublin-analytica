package ie.dublinanalytica.web.api;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import ie.dublinanalytica.web.user.BaseUser;
import ie.dublinanalytica.web.user.PasswordChangeDTO;
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
   * Gets the information about a specific user.
   *
   * @param authHeader The Authorization header (ADMIN only)
   * @param userid The user's id
   * @throws UserAuthenticationException If the user is not an admin
   * @throws UserNotFoundException If the user is not found
   */
  @GetMapping("/{userid:.{36}}")
  public Response getUserInfo(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable("userid") String userid)
      throws UserAuthenticationException, UserNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User requestingUser = userService.findById(payload.getId());
    if (!requestingUser.isAdmin()) {
      throw new UserAuthenticationException("User is not an admin", HttpStatus.FORBIDDEN);
    }

    UUID id = UUID.fromString(userid);
    User user = userService.findById(id);

    return new Response(user);
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

  /**
   * Update a user's name or email.
   *
   * @param authHeader The auth header
   * @param dto data
   * @throws UserAuthenticationException If token is invalid
   * @throws UserNotFoundException If the user couldn't be found
   */
  @PostMapping("/me")
  public Response updateUser(
      @RequestHeader("Authorization") String authHeader,
      @RequestBody BaseUser dto)
      throws UserAuthenticationException, UserNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    userService.verifyAuthToken(user, payload.getAuthToken());

    if (dto.getName() != null) {
      user.setName(dto.getName());
    }

    if (dto.getEmail() != null) {
      user.setEmail(dto.getEmail());
    }

    userService.save(user);

    return new EmptyResponse(HttpStatus.OK);
  }

  /**
   * Changes the user's password and returns a new jwt-token for auth.
   *
   * @param authHeader The auth header
   * @param dto data object
   * @throws UserAuthenticationException Invalid auth token
   * @throws UserNotFoundException User not found in the database
   * @throws WrongPasswordException Wrong old password field
   */
  @PostMapping("/me/password")
  public Response changePassword(
      @RequestHeader("Authorization") String authHeader,
      @RequestBody PasswordChangeDTO dto)
        throws UserAuthenticationException, UserNotFoundException, WrongPasswordException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());

    // Need copy because changePassword() will clear the password during verification
    char[] newPasswordCopy = dto.getNewPassword().clone();

    userService.verifyAuthToken(user, payload.getAuthToken());
    userService.changePassword(user, dto.getOldPassword(), dto.getNewPassword());
    String authToken = userService.createNewAuthToken(user, newPasswordCopy);
    return new AuthResponse(user, authToken);
  }
}
