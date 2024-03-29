package ie.dublinanalytica.web.api;

import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ie.dublinanalytica.web.api.response.EmptyResponse;
import ie.dublinanalytica.web.api.response.Response;
import ie.dublinanalytica.web.dataset.Dataset;
import ie.dublinanalytica.web.dataset.DatasetDTO;
import ie.dublinanalytica.web.dataset.DatasetService;
import ie.dublinanalytica.web.exceptions.DatasetAlreadyExistsException;
import ie.dublinanalytica.web.exceptions.DatasetNotFoundException;
import ie.dublinanalytica.web.exceptions.UserAuthenticationException;
import ie.dublinanalytica.web.exceptions.UserNotFoundException;
import ie.dublinanalytica.web.user.User;
import ie.dublinanalytica.web.user.UserService;
import ie.dublinanalytica.web.util.Utils;

/**
 * API Controller for /api/dataset endpoints.
 */
@RestController
@RequestMapping("/api/datasets")
public class DatasetAPIController {

  private DatasetService datasetService;
  private UserService userService;

  @Autowired
  public void setDatasetService(DatasetService datasetService) {
    this.datasetService = datasetService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  /**
   * Creates a new database.
   *
   * @param data Database information
   * @return Nothing on success, an error message on failure
   * @throws DatasetAlreadyExistsException if the dataset already exists
   */
  @PostMapping("")
  public Response create(@RequestHeader("Authorization") String authHeader,
                         @RequestBody @Valid DatasetDTO data)
      throws DatasetAlreadyExistsException, UserAuthenticationException, UserNotFoundException {

    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());

    userService.verifyAdmin(user);

    datasetService.createDataset(data);
    return new EmptyResponse(HttpStatus.CREATED);
  }

  /**
   * Returns an existing dataset.
   *
   * @param id Database id
   * @return Database
   * @throws DatasetNotFoundException if the dataset does not exist
   */
  @GetMapping("/{id:.{36}}")
  public Response getDataset(
      @RequestHeader(value = "Authorization", required = false) String authHeader,
      @PathVariable String id) throws
      DatasetNotFoundException, UserAuthenticationException, UserNotFoundException,
      JsonProcessingException {

    boolean isAdmin = false;

    if (authHeader != null) {
      JWTPayload payload = JWTPayload.fromHeader(authHeader);
      User user = userService.findById(payload.getId());
      isAdmin = user.isAdmin();
    }

    UUID uuid = UUID.fromString(id);
    Dataset dataset = datasetService.findById(uuid);

    if (isAdmin) {
      return new Response(dataset.toJSONAdmin(), MediaType.APPLICATION_JSON);
    }

    return new Response(dataset);
  }

  /**
   * Change a dataset.
   *
   * @param authHeader Authorization header, should be admin
   * @param id ID of the dataset
   * @param dto new information
   * @throws UserAuthenticationException if the user is not an admin
   * @throws UserNotFoundException if the user does not exist (invalid jwt token)
   * @throws DatasetNotFoundException If the dataset does not exist
   */
  @PostMapping("/{id:.{36}}")
  public Response editDataset(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable String id,
      @RequestBody DatasetDTO dto)
      throws UserAuthenticationException, UserNotFoundException, DatasetNotFoundException {
    JWTPayload payload = JWTPayload.fromHeader(authHeader);
    User user = userService.findById(payload.getId());
    userService.verifyAdmin(user);

    UUID uuid = UUID.fromString(id);
    Dataset dataset = datasetService.findById(uuid);

    if (dto.getName() != null) {
      dataset.setName(dto.getName());
    }

    if (dto.getDescription() != null) {
      dataset.setDescription(dto.getDescription());
    }

    if (dto.getSize() != null) {
      dataset.setSize(dto.getSize());
    }

    if (dto.getUnitPrice() != null) {
      dataset.setUnitPrice(dto.getUnitPrice());
    }

    if (dto.getImage() != null) {
      dataset.setImage(dto.getImage());
    }

    if (dto.isHidden() != null) {
      dataset.setHidden(dto.isHidden());
    }

    if (dto.getLink() != null) {
      dataset.setLink(dto.getLink());
      dataset.setSize(Utils.getSize(dto.getLink()));
    }

    datasetService.save(dataset);

    return new EmptyResponse(HttpStatus.OK);
  }

  /**
   * Returns all existing datasets.
   *
   */
  @SuppressWarnings("checkstyle:EmptyCatchBlock")
  @GetMapping("")
  public Response getAllDataset(
      @RequestHeader(value = "Authorization", required = false) String authHeader) {

    boolean isAdminTemp = false;

    if (authHeader != null) {
      try {
        JWTPayload payload = JWTPayload.fromHeader(authHeader);
        User user = userService.findById(payload.getId());
        isAdminTemp = user.isAdmin();
      } catch (Exception e) {
        // Do nothing. Will return non-hidden datasets.
        // Here auth is only used for admins to see hidden datasets
      }
    }

    // Captured variable should be final so using a temporary variable.
    final boolean isAdmin = isAdminTemp;
    Iterable<Dataset> datasets = datasetService.findAllDatasets();

    Stream<Dataset> filtered = StreamSupport.stream(
        datasets.spliterator(), false).filter(d -> isAdmin || !d.isHidden());

    if (!isAdmin) {
      return new Response(filtered);
    }

    StringJoiner joiner = new StringJoiner(",", "[", "]");

    filtered.forEach(d -> {
      try {
        joiner.add(d.toJSONAdmin());
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    });

    return new Response(joiner.toString(), MediaType.APPLICATION_JSON);
  }
}
