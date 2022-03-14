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

import ie.dublinanalytica.web.api.response.EmptyResponse;
import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.exceptions.ItemAlreadyExistsException;
import ie.dublinanalytica.web.exceptions.ShoppingCartNotFoundException;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.exceptions.UserNotFoundException;
import ie.dublinanalytica.web.shoppingcart.ItemDTO;
import ie.dublinanalytica.web.shoppingcart.ShoppingCartService;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;

/**
 * API Controller for /api/shoppingcart endpoints.
 */
@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartAPIController {

  private ShoppingCartService shoppingCartService;
  private UserService userService;

  @Autowired
  public void setShoppingCartService(ShoppingCartService shoppingCartService) {
    this.shoppingCartService = shoppingCartService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  /**
   * Gets the users shoppingcart.
   *
   * @param authHeader The Authorization header
   * @return Users shopping cart
   * @throws UserAuthenticationException if no authorization header is provided or
   *                                     is invalid
   * @throws UserNotFoundException       if the user not found
   * @throws ShoppingCartNotFoundException if cart does not exist
   */
  @GetMapping("/get")
  public Response getShoppingCart(@RequestHeader("Authorization") String authHeader)
      throws UserAuthenticationException, UserNotFoundException, ShoppingCartNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    return new Response(shoppingCartService.findById(user.getId()));
  }

  /**
   * Puts item into the users shoppingcart.
   *
   * @param authHeader The Authorization header
   * @return Nothing on success, an error message on failure
   * @throws UserAuthenticationException if no authorization header is provided or is invalid
   * @throws UserNotFoundException if the user not found
   * @throws ItemAlreadyExistsException if item already exists in cart
   */
  @PostMapping("/additem")
  public Response addToShoppingCart(@RequestHeader("Authorization") String authHeader, 
      @RequestBody @Valid ItemDTO item)
          throws UserAuthenticationException, UserNotFoundException, ItemAlreadyExistsException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    shoppingCartService.addToShoppingCart(user, item);
    return new EmptyResponse(HttpStatus.CREATED);
  }
}
