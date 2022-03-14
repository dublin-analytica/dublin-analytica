package ie.dublinanalytica.web.shoppingcart;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class for storing shopping cart.
 */
@Entity
public class ShoppingCart {
  
  @Id
  private UUID id;

  private HashMap<UUID, HashSet<Integer>> shoppingcart;

  public ShoppingCart(UUID id) {
    shoppingcart = new HashMap<>();
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public HashMap<UUID, HashSet<Integer>> getShoppingcart() {
    return shoppingcart;
  }

  public void setShoppingcart(HashMap<UUID, HashSet<Integer>> shoppingcart) {
    this.shoppingcart = shoppingcart;
  }

}
