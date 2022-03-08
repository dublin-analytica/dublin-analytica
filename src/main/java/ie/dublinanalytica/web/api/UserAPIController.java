package ie.dublinanalytica.web.api;

import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.api.response.ErrorResponse;
import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.user.AuthDTO;
import ie.dublinanalytica.web.user.BaseUser;
import ie.dublinanalytica.web.user.RegistrationDTO;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;
import ie.dublinanalytica.web.util.AuthUtils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
   *
   * @return A JSON object containing the information about the user and an auth token
   */
  @PostMapping("/login")
  public ResponseEntity<Response> login(@RequestBody AuthDTO data) {
    User user = userService.findByEmail(data.getEmail());

    if (user != null && user.verifyPassword(data.getPassword())) {
      String authToken = AuthUtils.generateAuthToken();
      userService.addAuthToken(user, authToken);

      Map<String, Object> payload = new HashMap<>() {{
          put("id", user.getId().toString());
          put("name", user.getName());
          put("email", user.getEmail());
          put("authToken", authToken);
        }};

      return new ResponseEntity<>(
          new AuthResponse(AuthUtils.createJwtToken(payload)), HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorResponse("Invalid email or password"), HttpStatus.UNAUTHORIZED);
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
      BaseUser baseUser = BaseUser.fromJwtToken(jwt);
      User user = userService.findById(baseUser.getId());
      user.removeAuthToken(jwt.getClaim("authToken").asString());
    } catch (IllegalArgumentException
        | JWTVerificationException
        | UserService.UserNotFoundException e) {
      return new ResponseEntity<>(new ErrorResponse("Invalid token"), HttpStatus.UNAUTHORIZED);
    }

    return new ResponseEntity<>("{}", HttpStatus.OK);
  }

  /**
   * Registers a new user.
   *
   * @param data Registration information
   *
   * @return Nothing on success, an error message on failure
   */
  @PostMapping("/register")
  public ResponseEntity<Response> register(@RequestBody @Valid RegistrationDTO data) {
    try {
      userService.registerUser(data);
    } catch (UserService.UserAlreadyExistsException e) {
      return new ResponseEntity<>(
          new ErrorResponse("User already exists"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Gets the current user's information.
   * Reads the JWT Token from the Authorization header.
   *
   * @param authHeader Authorization header.
   *
   * @return The user's information if the token is valid, an error message otherwise
   */
  @GetMapping("/me")
  public ResponseEntity<Object> me(@RequestHeader("Authorization") String authHeader) {
    try {
      DecodedJWT jwt = AuthUtils.getTokenFromHeader(authHeader);
      BaseUser baseUser = BaseUser.fromJwtToken(jwt);
      User user = userService.findById(baseUser.getId());
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (IllegalArgumentException
      | JWTVerificationException
      | UserService.UserNotFoundException e) {
      return new ResponseEntity<>(new ErrorResponse("Invalid token"), HttpStatus.UNAUTHORIZED);
    }
  }
}
