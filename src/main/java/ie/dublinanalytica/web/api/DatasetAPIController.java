package ie.dublinanalytica.web.api;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.dublinanalytica.web.api.response.EmptyResponse;
import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.dataset.Dataset;
import ie.dublinanalytica.web.dataset.DatasetDTO;
import ie.dublinanalytica.web.dataset.DatasetService;
import ie.dublinanalytica.web.exceptions.DatasetAlreadyExistsException;
import ie.dublinanalytica.web.exceptions.DatasetNotFoundException;

/**
 * API Controller for /api/dataset endpoints.
 */
@RestController
@RequestMapping("/api/dataset")
public class DatasetAPIController {

  private DatasetService datasetService;

  @Autowired
  public void setDatasetService(DatasetService datasetService) {
    this.datasetService = datasetService;
  }

  /**
   * Creates a new database.
   *
   * @param data Database information
   * @return Nothing on success, an error message on failure
   * @throws DatasetAlreadyExistsException if the dataset already exists
   */
  @PostMapping("/create")
  public Response create(@RequestBody @Valid DatasetDTO data)
      throws DatasetAlreadyExistsException {
    datasetService.createDataset(data);
    return new EmptyResponse(HttpStatus.CREATED);
  }

  /**
   * Returns an existing dataset.
   *
   * @param id Database id
   * @return Database
   * @throws DatasetAlreadyExistsException if the dataset already exists
   * @throws DatasetNotExistsException if the dataset does not exist
   */
  @GetMapping("/get/{id}")
  public Response getDataset(@PathVariable String id) throws 
      DatasetNotFoundException {
    UUID uuid = UUID.fromString(id);

    Dataset dataset = datasetService.findById(uuid);
    System.out.println(dataset.toString());
    return new Response(dataset);
  }

  /**
   * Returns all existing datasets.
   * 
   */
  @GetMapping("/get")
  public Response getAllDataset() {
    return new Response(datasetService.findAllDatasets());
  }
}
