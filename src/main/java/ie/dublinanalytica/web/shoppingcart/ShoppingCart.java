package ie.dublinanalytica.web.shoppingcart;

import ie.dublinanalytica.web.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

/**
 * Class for storing shopping cart.
 */
@Embeddable
public class ShoppingCart {

  @ElementCollection
  private Map<UUID, Integer> items;

  public ShoppingCart() {
  }

  public ShoppingCart(Map<UUID, Integer> items) {
    this.items = items;
  }

  /**
   * Set the count of the number of datapoints for a given dataset id.
   * Will remove the dataset if count is 0
   *
   * @param datasetId The id of the dataset
   * @param count The number of datapoints requested
   */
  public void put(UUID datasetId, int count) {
    if (count == 0) {
      items.remove(datasetId);
    } else {
      items.put(datasetId, count);
    }
  }

  public Map<UUID, Integer> getItems() {
    return items;
  }

  public void setItems(Map<UUID, Integer> items) {
    this.items = items;
  }

  public void clear() {
    items.clear();
  }
}
