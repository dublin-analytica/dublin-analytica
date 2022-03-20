package ie.dublinanalytica.web.api;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

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
import ie.dublinanalytica.web.dataset.Dataset;
import ie.dublinanalytica.web.dataset.DatasetService;
import ie.dublinanalytica.web.exceptions.DatasetNotFoundException;
import ie.dublinanalytica.web.exceptions.OrderNotFoundException;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.exceptions.UserNotFoundException;
import ie.dublinanalytica.web.orders.ChangeStatusDTO;
import ie.dublinanalytica.web.orders.Order;
import ie.dublinanalytica.web.orders.OrderService;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;
import ie.dublinanalytica.web.util.ZipFileBuilder;

/**
 * API Controller for /api/order endpoints.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderAPIController {

  private UserService userService;
  private OrderService orderService;
  private DatasetService datasetService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setOrderService(OrderService orderService) {
    this.orderService = orderService;
  }

  @Autowired
  public void setDatasetService(DatasetService datasetService) {
    this.datasetService = datasetService;
  }


  /**
   * Gets the users order.
   *
   * @param orderid Order id
   * @return Users order
   * @throws OrderNotFoundException      if the order not found
   */
  @GetMapping("/{orderid:.{36}}")
  public Response getOrder(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable("orderid") String orderid)
      throws OrderNotFoundException, UserAuthenticationException, UserNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    Order order = orderService.findById(UUID.fromString(orderid));

    if (order.getUser().getId() != user.getId() && !user.isAdmin()) {
      throw new UserAuthenticationException();
    }

    return new Response(order);
  }

  /**
   * Gets all orders.
   *
   * @param authHeader Authentication header
   * @throws UserAuthenticationException If the user is not an admin
   * @throws UserNotFoundException If the user is not found
   */
  @GetMapping("")
  public Response getAllOrders(@RequestHeader("Authorization") String authHeader)
      throws UserAuthenticationException, UserNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());

    if (!user.isAdmin()) {
      throw new UserAuthenticationException("User is not an admin", HttpStatus.FORBIDDEN);
    }

    return new Response(orderService.findAllOrders());
  }

  /**
   * Creates a downloadable zip file for a given order.
   *
   * @param authHeader Authentication header
   * @param orderid Order id
   * @throws UserAuthenticationException If the user is not an admin
   * @throws UserNotFoundException If the user is not found
   * @throws OrderNotFoundException If the order is not found
   * @throws DatasetNotFoundException If the dataset is not found
   * @throws IOException If the zip file cannot be created
   */
  @GetMapping("/{orderid:.{36}}/download")
  public byte[] downloadOrder(
      @RequestHeader(value = "Authorization", required = false) String authHeader,
      @PathVariable("orderid") String orderid,
      HttpServletResponse response)
      throws UserAuthenticationException, UserNotFoundException, OrderNotFoundException,
      DatasetNotFoundException, IOException {

    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());

    Order order = orderService.findById(UUID.fromString(orderid));

    if (order.getUser().getId() != user.getId() && !user.isAdmin()) {
      throw new UserAuthenticationException("You're not allowed to download this order",
          HttpStatus.FORBIDDEN);
    }

    ZipFileBuilder zip = new ZipFileBuilder();

    for (Map.Entry<UUID, Integer> entry : order.getItems().entrySet()) {
      Dataset d = datasetService.findById(entry.getKey());
      zip.addFile(d.getName() + ".csv", d.fetchFile().getBytes());
    }

    String filename = String.format("order_%s.zip", order.getId());

    response.setContentType("application/zip");
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader("Content-Disposition",  String.format("attachment; filename=\"%s\"", filename));

    return zip.build();
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
      throw new UserAuthenticationException("User is not an admin", HttpStatus.FORBIDDEN);
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

    if (authUser.getId().equals(user.getId()) || user.isAdmin()) {
      return new Response(orderService.getUserOrders(user));
    }

    throw new UserAuthenticationException("You're not allowed to access this resource",
      HttpStatus.FORBIDDEN);
  }
}
