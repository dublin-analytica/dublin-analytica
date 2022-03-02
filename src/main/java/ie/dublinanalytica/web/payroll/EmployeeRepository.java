package ie.dublinanalytica.web.payroll;

import org.springframework.data.repository.CrudRepository;

/**
 * EmployeeRepository.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}