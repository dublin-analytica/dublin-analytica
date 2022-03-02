package ie.dublinanalytica.web.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DatabaseLoader.
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

  /**
   * repository.
   */
  private final EmployeeRepository repository;

  /**
   * DatabaseLoader.
   *
   * @param repository repository
   */
  @Autowired
  public DatabaseLoader(final EmployeeRepository repository) {
    this.repository = repository;
  }

  /**
   * run.
   */
  @Override
  public void run(final String... strings) throws Exception {
    this.repository.save(new Employee("Frodo", "Baggins", "ring bearer"));
  }
}
