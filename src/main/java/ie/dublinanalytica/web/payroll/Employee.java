package ie.dublinanalytica.web.payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Employee.
 */
@Entity
public class Employee {

  /**
   * id.
   */
  private @Id @GeneratedValue Long id;
  /**
   * firstName.
   */
  private String firstName;
  /**
   * lastName.
   */
  private String lastName;
  /**
   * description.
   */
  private String description;

  protected Employee() {
  }

  /**
   * Employee.
   *
   * @param firstName first name
   * @param lastName last name
   * @param description description
   */
  public Employee(final String firstName, final String lastName, final String description) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return Objects.equals(id, employee.id)
        && Objects.equals(firstName, employee.firstName)
        && Objects.equals(lastName, employee.lastName)
        && Objects.equals(description, employee.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, description);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Employee{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", description='" + description + '\''
        + '}';
  }
}
