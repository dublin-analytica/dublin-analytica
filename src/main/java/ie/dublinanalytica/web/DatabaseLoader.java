package ie.dublinanalytica.web;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ie.dublinanalytica.web.dataset.Dataset;
import ie.dublinanalytica.web.dataset.DatasetRepository;
import ie.dublinanalytica.web.orders.Order;
import ie.dublinanalytica.web.orders.OrderRepository;
import ie.dublinanalytica.web.shoppingcart.ShoppingCart;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserRepository;

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

  /**
   * Initilizes different instances of classes into repositorys for testing.
   */
  @Autowired
  public DatabaseLoader(UserRepository repository, DatasetRepository datasetRepository,
      OrderRepository orderRepository) {
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

    set.setImage("https://cloudfront-us-east-1.images.arcpublishing.com/coindesk/XA6KIXE6FBFM5EWSA25JI5YAU4.jpg");

    this.datasetRepository.save(set);

    set = new Dataset("Another dataset", "Another great dataset", "no", 500, "www.com");
    set.setImage("https://preview.redd.it/o6y07vrwfz561.jpg?auto=webp&s=6982d23e08c8f3e5a1e8e39dbca01aa71609fed2");


    this.datasetRepository.save(set);

    Dataset hiddenSet = new Dataset(
        "Hidden dataset", "This dataset is hidden by default", "no", 500, "www.com");
    hiddenSet.setHidden(true);
    hiddenSet.setImage("https://i.insider.com/602ee9ced3ad27001837f2ac?width=750&format=jpeg&auto=webp");

    this.datasetRepository.save(hiddenSet);

    HashMap<UUID, Integer> map = new HashMap<>();
    map.put(set.getId(), 10);
    Order order = new Order(new ShoppingCart(map), admin, 10);
    order.setStatus(Order.OrderStatus.PROCESSING);

    this.orderRepository.save(order);

    this.orderRepository.save(
      new Order(new ShoppingCart(map), admin, 10)
    );
  }
}
