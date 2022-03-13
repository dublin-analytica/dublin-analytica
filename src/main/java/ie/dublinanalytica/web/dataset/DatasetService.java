package ie.dublinanalytica.web.dataset;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.dublinanalytica.web.exceptions.DatasetAlreadyExistsException;
import ie.dublinanalytica.web.exceptions.DatasetNotFoundException;

/**
 * A service class to handle Dataset related operations.
 */
@Service
public class DatasetService {

  private DatasetRepository repository;

  @Autowired
  public void setDatasetRepository(DatasetRepository repository) {
    this.repository = repository;
  }

  /**
   * Checks whether a dataset with a given name exists.
   *
   * @param name The name of dataset to check
   * @return true if a dataset with the given name exists, false otherwise
   */
  private boolean datasetExists(String name) {
    return this.repository.findByName(name) != null;
  }

  /**
   * Creates a new dataset.
   *
   * @param data The DTO containing the User data required for registration
   * @throws DatasetAlreadyExistsException if a user with the given email already
   *                                       exists
   */
  public void createDataset(DatasetDTO data) throws DatasetAlreadyExistsException {
    if (datasetExists(data.getName())) {
      throw new DatasetAlreadyExistsException();
    }

    repository.save(new Dataset(data));
  }

  /**
   * Finds a dataset by their name.
   *
   * @param name The datasets name
   * @return The Dataset object
   * @throws DatasetNotFoundException If the dataset with the given name doesn't
   *                               exist
   */
  public Dataset findByName(String name) throws DatasetNotFoundException {
    Dataset dataset = this.repository.findByName(name);
    if (dataset == null) {
      throw new DatasetNotFoundException();
    }
    return this.repository.findByName(name);
  }

  /**
   * Finds a dataset by their id.
   *
   * @param id The datasets id
   * @return The Dataset object
   * @throws DatasetNotFoundException If the dataset with the given id doesn't
   *                               exist
   */
  public Dataset findById(UUID id) throws DatasetNotFoundException {
    Optional<Dataset> dataset = this.repository.findById(id);
    if (dataset.isEmpty()) {
      throw new DatasetNotFoundException();
    }
    return dataset.get();
  }
}
