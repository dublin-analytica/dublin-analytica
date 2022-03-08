package ie.dublinanalytica.web.user;

import org.springframework.data.repository.CrudRepository;

/**
 * UserRepository for accessing the User entity database.
 */
public interface UserRepository extends CrudRepository<User, Long> {
  User findByEmail(String email);
}
