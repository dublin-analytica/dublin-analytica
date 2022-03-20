package ie.dublinanalytica.web.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ie.dublinanalytica.web.api.response.EmptyResponse;
import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.dataset.Dataset;
import ie.dublinanalytica.web.dataset.DatasetService;
import ie.dublinanalytica.web.exceptions.BadRequest;
import ie.dublinanalytica.web.exceptions.DatasetNotFoundException;
import ie.dublinanalytica.web.exceptions.InvalidCardExpiryDate;
import ie.dublinanalytica.web.exceptions.InvalidCardNumber;
import ie.dublinanalytica.web.exceptions.InvalidCvvNumber;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.exceptions.UserNotFoundException;
import ie.dublinanalytica.web.orders.OrderService;
import ie.dublinanalytica.web.shoppingcart.CardDTO;
import ie.dublinanalytica.web.shoppingcart.ItemDTO;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;

/**
 * API Controller for /api/cart endpoints.
 */
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartAPIController {

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
   * Gets the users shopping cart.
   *
   * @param authHeader The Authorization header
   * @return Users shopping cart
   * @throws UserAuthenticationException if no authorization header is provided or
   *                                     is invalid
   * @throws UserNotFoundException       if the user not found
   */
  @GetMapping("")
  public Response getShoppingCart(@RequestHeader("Authorization") String authHeader)
      throws UserAuthenticationException, UserNotFoundException, DatasetNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());

    Map<UUID, Integer> items = userService.getCart(user, payload.getAuthToken());

    List<Dataset> formattedItems = new ArrayList<>();

    for (UUID id : items.keySet()) {
      Dataset dataset = datasetService.findById(id);
      dataset.setSize(items.get(id));
      formattedItems.add(dataset);
    }

    return new Response(formattedItems);
  }

  /**
   * Puts item into the users shopping cart.
   *
   * @param authHeader The Authorization header
   * @return Nothing on success, an error message on failure
   * @throws UserAuthenticationException if no authorization header is provided or is invalid
   * @throws UserNotFoundException if the user not found
   */
  @PostMapping("")
  public Response addToShoppingCart(
      @RequestHeader("Authorization") String authHeader,
      @RequestBody @Valid ItemDTO item)
      throws UserAuthenticationException, UserNotFoundException,
      DatasetNotFoundException, BadRequest {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    userService.addToCart(user, payload.getAuthToken(), item);
    return new EmptyResponse(HttpStatus.CREATED);
  }

  /**
   * Sets the users shopping cart to the given item.
   *
   * @param authHeader The authorization header
   * @param item The item info
   * @throws UserAuthenticationException if no authorization header is provided or is invalid
   * @throws UserNotFoundException if the user not found
   * @throws BadRequest if the request is invalid
   * @throws DatasetNotFoundException if the dataset not found
   */
  @PatchMapping("")
  public Response updateShoppingCart(
      @RequestHeader("Authorization") String authHeader,
      @RequestBody @Valid ItemDTO item)
      throws UserAuthenticationException, UserNotFoundException,
              BadRequest, DatasetNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    userService.updateCart(user, payload.getAuthToken(), item);
    return new EmptyResponse(HttpStatus.CREATED);
  }

  /**
   * User checkout.
   *
   * @param authHeader The authorization header
   * @throws UserAuthenticationException if no authorization header is provided or is invalid
   * @throws UserNotFoundException if the user not found
   */
  @PostMapping("/checkout")
  public Response confirmCheckout(@RequestHeader("Authorization") String authHeader,
                                  @RequestBody @Valid CardDTO card)
      throws UserAuthenticationException, UserNotFoundException, BadRequest,
      InvalidCvvNumber, InvalidCardExpiryDate, ParseException,
      InvalidCardNumber, DatasetNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    orderService.verifyCardPayment(card);
    orderService.placeOrder(user, payload.getAuthToken());
    return new EmptyResponse(HttpStatus.CREATED);
  }
}
