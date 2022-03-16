package ie.dublinanalytica.web.orders;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import ie.dublinanalytica.web.user.User;

/**
 * Repository for orders.
 */
public interface OrderRepository extends CrudRepository<Order, UUID> {

  List<Order> findByUser(User user);
}
