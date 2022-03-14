package ie.dublinanalytica.web.shoppingcart;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

/**
 * ShoppingCartRepository for accessing the ShoppingCart entity database.
 */
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, UUID> {
  
}
