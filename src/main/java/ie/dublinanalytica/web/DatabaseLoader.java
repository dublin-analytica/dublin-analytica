package ie.dublinanalytica.web;

import ie.dublinanalytica.web.orders.Order;
import ie.dublinanalytica.web.orders.OrderRepository;
import ie.dublinanalytica.web.shoppingcart.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import ie.dublinanalytica.web.dataset.Dataset;
import ie.dublinanalytica.web.dataset.DatasetRepository;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Pre-save data into the database.
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

  /**
   * User repository.
   */
  private final UserRepository userRepository;

  /**
   * Dataset repository.
   */
  private final DatasetRepository datasetRepository;

  /**
   * Order repository.
   */
  private final OrderRepository orderRepository;

  @Autowired
  public DatabaseLoader(UserRepository repository, DatasetRepository datasetRepository, OrderRepository orderRepository) {
    this.userRepository = repository;
    this.datasetRepository = datasetRepository;
    this.orderRepository = orderRepository;
  }

  @Override
  public void run(final String... strings) throws Exception {
    this.userRepository.save(
      new User("Alice the User", "user@gmail.com", "user".toCharArray())
    );

    User admin = new User("Bob the Admin", "admin@gmail.com", "admin".toCharArray());
    admin.setAdmin(true);

    this.userRepository.save(admin);

    Dataset set = new Dataset("Some dataset", "An amazing dataset", "datapoints", 1000, "www.com");

    this.datasetRepository.save(set);

    this.datasetRepository.save(
      new Dataset("Another dataset", "Another great dataset", "no", 500, "www.com")
    );

    HashMap<UUID, Integer> map = new HashMap<>();
    map.put(set.getId(), 10);
    Order order = new Order(new ShoppingCart(map), admin);
    order.setStatus(Order.OrderStatus.PROCESSING);

    this.orderRepository.save(order);

    this.orderRepository.save(
      new Order(new ShoppingCart(map), admin)
    );
  }
}
