package ie.dublinanalytica.web.orders;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import ie.dublinanalytica.web.shoppingcart.ShoppingCart;
import ie.dublinanalytica.web.user.User;

/**
 * Class for storing orders.
 */
@Entity
@Table(name = "orders")
public class Order {

  /**
   * Enum to represent the status of the order.
   */
  public enum OrderStatus {
    PLACED, PROCESSING, DELIVERED
  }

  private LocalDateTime timestamp;

  @Id
  @GeneratedValue
  private UUID id;

  @ElementCollection
  private Map<UUID, Integer> items;

  @ManyToOne
  private User user;

  private OrderStatus status;

  public Order() {
    this.status = OrderStatus.PLACED;
    this.timestamp = LocalDateTime.now();
  }

  /**
   * Create an order for a user from a shopping cart.
   *
   * @param cart The cart to use
   * @param user The user to create the order for
   */
  public Order(ShoppingCart cart, User user) {
    this();
    this.items = new HashMap<>(cart.getItems());
    this.user = user;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public UUID getId() {
    return id;
  }

  public void setItems(Map<UUID, Integer> items) {
    this.items = items;
  }

  public Map<UUID, Integer> getItems() {
    return items;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }
}
