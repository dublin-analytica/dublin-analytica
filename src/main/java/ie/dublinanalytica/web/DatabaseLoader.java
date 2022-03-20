package ie.dublinanalytica.web;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.StreamSupport;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

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
   * Initialises different instances of classes into repositories for testing.
   */
  @Autowired
  public DatabaseLoader(UserRepository repository, DatasetRepository datasetRepository,
      OrderRepository orderRepository) {
    this.userRepository = repository;
    this.datasetRepository = datasetRepository;
    this.orderRepository = orderRepository;
  }

  private final Lorem lorem = LoremIpsum.getInstance();

  @Override
  public void run(final String... args) throws Exception {
    populateDatabase();
  }

  /**
   * Populates the database with dummy data.
   */
  public void populateDatabase() {
    orderRepository.deleteAll();
    datasetRepository.deleteAll();
    userRepository.deleteAll();

    User alice = new User("Alice the admin", "alice@gmail.com", "Alice&bob1".toCharArray(), true);
    User bob = new User("Bob the businessman", "bob@gmail.com", "Alice&bob1".toCharArray(), false);
    User user = new User("User", "user@gmail.com", "Alice&bob1".toCharArray(), false);

    userRepository.save(alice);
    userRepository.save(bob);
    userRepository.save(user);

    Random rand = new Random();

    for (int i = 0; i < 50; i++) {
      String name = "Dataset " + i;
      String description = lorem.getWords(rand.nextInt(10, 25));

      Dataset set = new Dataset(
          name,
          description,
          rand.nextInt(100, 10000), ""
      );

      set.setImage("https://i.imgflip.com/db5xf.jpg");

      double price = Math.round(rand.nextDouble(0.0001, 0.01) * Math.pow(10, 5)) / Math.pow(10, 5);

      set.setUnitPrice(price);

      if (rand.nextFloat() < 0.15) {
        set.setHidden(true);
      }

      datasetRepository.save(set);
    }

    Iterable<Dataset> datasets = datasetRepository.findAll();
    List<Dataset> all = StreamSupport.stream(datasets.spliterator(), false).toList();

    for (int i = 0; i < 30; i++) {

      HashMap<UUID, Integer> map = new HashMap<>();

      User owner = rand.nextBoolean() ? alice : bob;

      for (int j = 0; j < rand.nextInt(2, 10); j++) {
        Dataset set = all.get(rand.nextInt(all.size()));
        map.put(set.getId(), rand.nextInt(10, set.getSize()));
      }

      Date date = new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime();

      Order order = new Order(
          new ShoppingCart(map), owner,
          Math.round(rand.nextFloat(10, 500) * Math.pow(10, 2)) / Math.pow(10, 2));

      order.setTimestamp(
          LocalDateTime.ofEpochSecond(
            rand.nextInt((int) date.toInstant().getEpochSecond(), (int) (new Date().toInstant().getEpochSecond())),
            0, ZoneOffset.UTC));

      this.orderRepository.save(order);
    }

  }
}
