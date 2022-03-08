package ie.dublinanalytica.web.user;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

/**
 * UserRepository for accessing the User entity database.
 */
public interface UserRepository extends CrudRepository<User, UUID> {
  User findByEmail(String email);

  boolean existsByEmail(String email);
}
