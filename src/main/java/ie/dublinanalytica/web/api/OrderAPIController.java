package ie.dublinanalytica.web.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.exceptions.OrderNotFoundException;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.exceptions.UserNotFoundException;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;

/**
 * API Controller for /api/order endpoints.
 */
@RestController
@RequestMapping("/api/order")
public class OrderAPIController {
  
  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  /**
   * Gets the users order.
   *
   * @param userid User id
   * @param orderid Order id
   * @return Users order
   * @throws UserAuthenticationException if no authorization header is provided or
   *                                     is invalid
   * @throws UserNotFoundException       if the user not found
   * @throws OrderNotFoundException      if the order not found
   */
  @GetMapping("/{userid}/{orderid}")
  public Response getShoppingCart(@PathVariable("userid") String userid, 
      @PathVariable("orderid") String orderid)
      throws UserAuthenticationException, UserNotFoundException, OrderNotFoundException {
    UUID uuid = UUID.fromString(userid);
    User user = userService.findById(uuid);
    return new Response(userService.getOrder(user, UUID.fromString(orderid)));
  }

  /**
   * Gets all the users order.
   *
   * @param userid User id
   * @return All users order
   * @throws UserAuthenticationException if no authorization header is provided or
   *                                     is invalid
   * @throws UserNotFoundException       if the user not found
   * @throws OrderNotFoundException      if the order not found
   */
  @GetMapping("/{userid}")
  public Response getShoppingCart(@PathVariable("userid") String userid)
      throws UserAuthenticationException, UserNotFoundException, OrderNotFoundException {
    UUID uuid = UUID.fromString(userid);
    User user = userService.findById(uuid);
    return new Response(userService.getAllOrders(user));
  }
}
