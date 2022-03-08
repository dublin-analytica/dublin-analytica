package ie.dublinanalytica.web.api;

import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.api.response.ErrorResponse;
import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.user.AuthDTO;
import ie.dublinanalytica.web.user.RegistrationDTO;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;
import ie.dublinanalytica.web.util.Authentication;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    System.out.printf("/api/users/login : %s, %s\n",
        data.getEmail(), Arrays.toString(data.getPassword()));
    User user = userService.findByEmail(data.getEmail());

    if (user != null && user.verifyPassword(data.getPassword())) {
      String authToken = Authentication.generateAuthToken();
      userService.addAuthToken(user, authToken);

      Map<String, Object> payload = new HashMap<>() {{
          put("id", user.getId());
          put("name", user.getName());
          put("email", user.getEmail());
          put("authToken", authToken);
        }};

      String token = Authentication.createJwtToken(payload);

      return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorResponse("Invalid email or password"), HttpStatus.UNAUTHORIZED);
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
    System.out.printf("/api/users/register : %s, %s, %s\n",
        data.getName(), data.getEmail(), Arrays.toString(data.getPassword()));
    try {
      userService.registerUser(data);
    } catch (UserService.UserAlreadyExistsException e) {
      return new ResponseEntity<>(
          new ErrorResponse("User already exists"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(HttpStatus.OK);
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
  public ResponseEntity<Response> me(@RequestHeader("Authorization") String authHeader) {
    String jwtToken = authHeader.substring(7);

    System.out.printf("/api/users/me : %s\n", jwtToken);

    // Verify Token
    DecodedJWT jwt;
    try {
      jwt = Authentication.decodeJwtToke(jwtToken);
    } catch (JWTVerificationException e) {
      return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    Optional<User> userOptional = userService.findById(jwt.getClaim("id").asLong());

    if (userOptional.isEmpty()) {
      return new ResponseEntity<>(
          new ErrorResponse("Invalid JWT Token"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    User user = userOptional.get();

    System.out.printf("User: %s\n", user);

    return new ResponseEntity<>(new Response(user), HttpStatus.OK);
  }
}
