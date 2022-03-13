package ie.dublinanalytica.web.dataset;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

/**
 * DatasetRepository for accessing the Dataset entity database.
 */
public interface DatasetRepository extends CrudRepository<Dataset, UUID> {

  Dataset findByName(String name);
}
