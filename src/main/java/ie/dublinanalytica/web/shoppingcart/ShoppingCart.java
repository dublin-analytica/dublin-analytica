package ie.dublinanalytica.web.shoppingcart;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

/**
 * Class for storing shopping cart.
 */
@Embeddable
public class ShoppingCart {

  @ElementCollection
  private Set<ItemDTO> items;

  public ShoppingCart() {
  }

  public void addItem(ItemDTO item) {
    items.add(item);
  }

  public Set<ItemDTO> getItems() {
    return items;
  }

  public void setItems(Set<ItemDTO> items) {
    this.items = items;
  }

  public void clear() {
    items.clear();
  }
}
