package ie.dublinanalytica.web.shoppingcart;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.dublinanalytica.web.dataset.Dataset;
import ie.dublinanalytica.web.exceptions.ItemAlreadyExistsException;
import ie.dublinanalytica.web.exceptions.ShoppingCartNotFoundException;
import ie.dublinanalytica.web.user.User;

/**
 * A service class to handle Shopping Cart related operations.
 */
@Service
public class ShoppingCartService {
  
  private ShoppingCartRepository repository;

  @Autowired
  public void setShoppingCartRepository(ShoppingCartRepository repository) {
    this.repository = repository;
  }

  /**
   * Checks whether an item in a peron shopping cart exists.
   *
   * @param user The customer
   * @param item The item
   * @return shoppingcart if it doesnt contain item, throws exception if it does
   * @throws ItemAlreadyExistsException if item is already in the cart.
   */
  private ShoppingCart itemExists(User user, ItemDTO item) throws ItemAlreadyExistsException {
    Optional<ShoppingCart> shoppingcart = this.repository.findById(user.getId());
    if (shoppingcart.isEmpty()) {
      return new ShoppingCart(user.getId());
    }

    ShoppingCart cart = shoppingcart.get();
    if (cart.getShoppingcart().containsKey(item.getDatasetId())) {
      HashSet<Integer> datapoints = cart.getShoppingcart().get(item.getDatasetId());
      for (Integer x : item.getDatapoints()) {
        if (datapoints.contains(x)) { 
          throw new ItemAlreadyExistsException();
        }
      }
    }

    return cart;
  }

  /**
   * Adds an item to the shopping cart.
   *
   * @param item The DTO containing the item to be added.
   * @param user The customer shopping cart.
   * @throws ItemAlreadyExistsException if item is already in the cart.
   */
  public void addToShoppingCart(User user, ItemDTO item) throws ItemAlreadyExistsException {
    ShoppingCart shoppingcart = itemExists(user, item);
    shoppingcart.getShoppingcart().put(item.getDatasetId(), item.getDatapoints());
    repository.save(shoppingcart);
  }

  /**
   * Finds a shopping cart by their id.
   *
   * @param id The user id
   * @return The shopping cart
   * @throws ShoppingCartNotFoundException If the shopping cart with the given id doesn't
   *                               exist
   */
  public ShoppingCart findById(UUID id) throws ShoppingCartNotFoundException {
    Optional<ShoppingCart> dataset = this.repository.findById(id);
    if (dataset.isEmpty()) {
      throw new ShoppingCartNotFoundException();
    }
    return dataset.get();
  }
}
