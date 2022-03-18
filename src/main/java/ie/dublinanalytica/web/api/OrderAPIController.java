package ie.dublinanalytica.web.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ie.dublinanalytica.web.api.response.EmptyResponse;
import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.exceptions.OrderNotFoundException;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.exceptions.UserNotFoundException;
import ie.dublinanalytica.web.orders.ChangeStatusDTO;
import ie.dublinanalytica.web.orders.Order;
import ie.dublinanalytica.web.orders.OrderService;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;

/**
 * API Controller for /api/order endpoints.
 */
@RestController
@RequestMapping("/api/order")
public class OrderAPIController {

  private UserService userService;
  private OrderService orderService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setOrderService(OrderService orderService) {
    this.orderService = orderService;
  }


  /**
   * Gets the users order.
   *
   * @param orderid Order id
   * @return Users order
   * @throws OrderNotFoundException      if the order not found
   */
  @GetMapping("/{orderid}")
  public Response getOrder(@PathVariable("orderid") String orderid)
      throws OrderNotFoundException {
    return new Response(userService.getOrder(UUID.fromString(orderid)));
  }

  @GetMapping("/")
  public Response getAllOrders() throws OrderNotFoundException {
    return new Response(orderService.findAllOrders());
  }


  /**
   * Changes the status of an order.
   *
   * @param authHeader The Authorization header
   * @param dto object to change the status of an order
   * @throws UserAuthenticationException If the user isn't authenticated
   * @throws UserNotFoundException If the user isn't found
   * @throws OrderNotFoundException If the order isn't found
   */
  @PostMapping("/{orderid}/status")
  public Response updateOrderStatus(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable("orderid") String orderid,
      @RequestBody ChangeStatusDTO dto)
      throws UserAuthenticationException, UserNotFoundException, OrderNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);

    User user = userService.findById(payload.getId());

    if (!user.isAdmin()) {
      throw new UserAuthenticationException("User is not an admin");
    }

    UUID orderUUID = UUID.fromString(orderid);
    Order order = orderService.findById(orderUUID);
    order.setStatus(dto.getStatus());
    orderService.save(order);

    return new EmptyResponse(HttpStatus.OK);
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
  @GetMapping("/user/{userid}")
  public Response getShoppingCart(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable(value = "userid", required = false) String userid)
        throws UserAuthenticationException, UserNotFoundException, OrderNotFoundException {

    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User authUser = userService.findById(payload.getId());

    UUID uuid = UUID.fromString(userid);
    User user = userService.findById(uuid);

    if (authUser.getId().equals(user.getId())) {
      return new Response(orderService.getUserOrders(user));
    }

    throw new UserAuthenticationException("Not authorized");
  }
}
