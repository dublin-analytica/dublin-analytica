package ie.dublinanalytica.web.orders;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Embeddable;

import ie.dublinanalytica.web.shoppingcart.ShoppingCart;

/**
 * Class for storing orders.
 */
@Embeddable
public class Order extends ShoppingCart {
  
  private LocalDateTime timestamp;
  private UUID id;

  public Order() {

  }
  
  public Order(ShoppingCart cart) {
    super(cart.getItems());
    this.id = UUID.randomUUID();
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


}
