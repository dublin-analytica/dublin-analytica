package ie.dublinanalytica.web.orders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ie.dublinanalytica.web.dataset.DatasetService;
import ie.dublinanalytica.web.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ie.dublinanalytica.web.shoppingcart.CardDTO;
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
  private DatasetService datasetService;

  @Autowired
  public void setOrderRepository(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Autowired
  public void setDatasetService(DatasetService datasetService) {
    this.datasetService = datasetService;
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
  public void placeOrder(User user, String token) throws UserAuthenticationException,
      BadRequest, DatasetNotFoundException {
    userService.verifyAuthToken(user, token);

    ShoppingCart cart = user.getCart();

    if (cart.getItems().isEmpty()) {
      throw new BadRequest("Cart is empty");
    }

    Order newOrder = new Order(cart, user, getCartPrice(cart));

    user.getCart().clear();

    userService.save(user);

    orderRepository.save(newOrder);
  }

  public double getCartPrice(ShoppingCart cart) throws DatasetNotFoundException {
    double price = 0;
    for (Map.Entry<UUID, Integer> entry : cart.getItems().entrySet()) {
      UUID key = entry.getKey();
      int value = entry.getValue();
      price += datasetService.findById(key).getUnitPrice() * value;
    }
    return price;
  }

  /**
   * Finds an order using the order's id.
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

  /**
   * Finds all orders in crud repository.
   *
   * @return All the Order objects
   */
  public Iterable<Order> findAllOrders() {
    return orderRepository.findAll();
  }

  public void verifyCardPayment(CardDTO card) throws
      InvalidCvvNumber, InvalidCardNumber, ParseException, InvalidCardExpiryDate {
    validateCardNumberAndCvv(card.getCardNum(), card.getCvv());
    validateCardDate(card.getExpiry());
  }

  private void validateCardDate(String cardExpiry) throws ParseException, InvalidCardExpiryDate {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
    simpleDateFormat.setLenient(false);
    Date expiry = simpleDateFormat.parse(cardExpiry);
    if (expiry.before(new Date())) {
      throw new InvalidCardExpiryDate();
    }
  }

  /**
   * Validates the card expiration and the card number. 
   * VISA, MASTERCARD and AMEX are supported.
   */
  public void validateCardNumberAndCvv(String cardNum, String cvv) throws
      InvalidCardNumber, InvalidCvvNumber {
    if (cardNum.length() < 13 || cardNum.length() > 16) {
      throw new InvalidCardNumber();
    }

    String regex = "^(?:(4[0-9]{12}(?:[0-9]{3})?)|" 
        + "(5[1-5][0-9]{14})|" + "(3[47][0-9]{13}))$";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(cardNum);

    if (!matcher.matches()) {
      throw new InvalidCardNumber();
    }

    if (cardNum.charAt(0) == '3') {
      regex = "^[0-9]{4}$";
    } else {
      regex = "^[0-9]{3}$";
    }

    Pattern cvvPattern = Pattern.compile(regex);
    Matcher cvvMatcher = cvvPattern.matcher(cvv);
    
    if (!cvvMatcher.matches()) {
      throw new InvalidCvvNumber();
    }

    int sum = 0;
    boolean alternate = false;
    for (int i = cardNum.length() - 1; i >= 0; i--) {
      int n = Integer.parseInt(cardNum.substring(i, i + 1));
      if (alternate) {
        n *= 2;
        if (n > 9) {
          n = (n % 10) + 1;
        }
      }
      sum += n;
      alternate = !alternate;
    }

    if (sum % 10 != 0) {
      throw new InvalidCardNumber();
    }
  }
}
