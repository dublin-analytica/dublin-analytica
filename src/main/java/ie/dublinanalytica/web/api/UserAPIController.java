package ie.dublinanalytica.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.api.response.ErrorResponse;
import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.user.AuthDTO;
import ie.dublinanalytica.web.user.BaseUser;
import ie.dublinanalytica.web.user.RegistrationDTO;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;
import ie.dublinanalytica.web.util.AuthUtils;

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
   */
  @PostMapping("/login")
  public ResponseEntity<Response> login(@RequestBody AuthDTO data) {
    if (!userService.userExists(data.getEmail())) {
      new ResponseEntity<>(
          new ErrorResponse(
          UserService.UserNotFoundException.class.getSimpleName(),
          UserService.UserNotFoundException.defaultMessage), HttpStatus.UNAUTHORIZED);
    }

    User user = userService.findByEmail(data.getEmail());

    if (user.verifyPassword(data.getPassword())) {
      String token = AuthUtils.generateAuthToken();
      userService.addAuthToken(user, token);

      Map<String, Object> payload = new HashMap<>() {{
          put("token", token);
        }};

      return new ResponseEntity<>(
          new AuthResponse(AuthUtils.createJWT(payload)), HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorResponse(
        UserService.UserAuthenticationException.class.getSimpleName(),
        UserService.UserAuthenticationException.defaultMessage), HttpStatus.UNAUTHORIZED);
  }

  /**
   * Performs logout for a user by revoking their auth token.
   *
   * @param authHeader The Authorization header
   * @return 200 OK on success, error message on failure
   */
  @PostMapping("/logout")
  public ResponseEntity<Object> login(@RequestHeader("Authorization") String authHeader) {
    try {
      DecodedJWT jwt = AuthUtils.getTokenFromHeader(authHeader);
      BaseUser baseUser = BaseUser.fromJWT(jwt);
      User user = userService.findById(baseUser.getId());
      userService.verifyAuthToken(user, jwt.getClaim("authToken").asString());
      user.removeAuthToken(jwt.getClaim("authToken").asString());

    } catch (UserService.BaseUserException e) {
      return new ResponseEntity<>(
          new ErrorResponse(e.getName(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    return new ResponseEntity<>("{}", HttpStatus.OK);
  }

  /**
   * Registers a new user.
   *
   * @param data Registration information
   * @return Nothing on success, an error message on failure
   */
  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody @Valid RegistrationDTO data) {
    try {
      userService.registerUser(data);
    } catch (UserService.BaseUserException e) {
      return new ResponseEntity<>(
          new ErrorResponse(e.getName(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>("{}", HttpStatus.CREATED);
  }

  /**
   * Gets the current user's information.
   * Reads the JWT Token from the Authorization header.
   *
   * @param authHeader Authorization header.
   * @return The user's information if the token is valid, an error message otherwise
   */
  @GetMapping("/me")
  public ResponseEntity<Object> me(@RequestHeader("Authorization") String authHeader) {
    try {
      DecodedJWT jwt = AuthUtils.getTokenFromHeader(authHeader);
      BaseUser baseUser = BaseUser.fromJWT(jwt);
      User user = userService.findById(baseUser.getId());
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (UserService.BaseUserException e) {
      return new ResponseEntity<>(
          new ErrorResponse(e.getName(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }
}
