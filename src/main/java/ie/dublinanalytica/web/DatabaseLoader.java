package ie.dublinanalytica.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import ie.dublinanalytica.web.dataset.Dataset;
import ie.dublinanalytica.web.dataset.DatasetRepository;
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

  @Autowired
  public DatabaseLoader(UserRepository repository, DatasetRepository datasetRepository) {
    this.userRepository = repository;
    this.datasetRepository = datasetRepository;
  }

  @Override
  public void run(final String... strings) throws Exception {
    this.userRepository.save(
      new User("Alice the User", "user@gmail.com", "user".toCharArray())
    );

    User admin = new User("Bob the Admin", "admin@gmail.com", "admin".toCharArray());
    admin.setAdmin(true);

    this.userRepository.save(admin);

    this.datasetRepository.save(
      new Dataset("Some dataset", "An amazing dataset", "datapoints", 1000, "www.com")
    );

    this.datasetRepository.save(
      new Dataset("Another dataset", "Another great dataset", "no", 500, "www.com")
    );
  }
}
