package ie.dublinanalytica.web.orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import ie.dublinanalytica.web.exceptions.BadRequest;
import ie.dublinanalytica.web.exceptions.OrderNotFoundException;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.shoppingcart.ShoppingCart;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;

/**
 * Service for managing orders.
 */
@Service
public class OrderService {

  private OrderRepository orderRepository;
  private UserService userService;

  @Autowired
  public void setUserRepository(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Autowired
  public void setUserService(@Lazy UserService userService) {
    this.userService = userService;
  }

  public List<Order> getUserOrders(User user) {
    return orderRepository.findByUser(user);
  }

  /**
   * Places an order.
   *
   * @param user the customer user
   */
  public void placeOrder(User user, String token) throws UserAuthenticationException, BadRequest {
    userService.verifyAuthToken(user, token);

    ShoppingCart cart = user.getCart();

    if (cart.getItems().isEmpty()) {
      throw new BadRequest("Cart is empty");
    }

    Order newOrder = new Order(cart, user);

    user.getCart().clear();

    orderRepository.save(newOrder);
  }

  /**
   * Finds an oder using the order's id.
   *
   * @param id The id of the order
   * @return The order
   * @throws OrderNotFoundException If the order wasn't found
   */
  public Order findById(UUID id) throws OrderNotFoundException {
    Optional<Order> order = orderRepository.findById(id);

    if (order.isPresent()) {
      return order.get();
    }

    throw new OrderNotFoundException();
  }

  public void save(Order order) {
    orderRepository.save(order);
  }
}
