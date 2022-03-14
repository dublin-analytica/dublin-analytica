package ie.dublinanalytica.web.dataset;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class for storing a dataset.
 */
@Entity
@JsonIgnoreProperties(value = {"url"})
public class Dataset {
  @Id
  @GeneratedValue
  private UUID id;

  private String name;
  private String description;
  private String datapoints;
  private double size;
  private String url;

  public Dataset() {
  }

  /**
   * Creates a Dataset from DatasetDTO.
   *
   * @param data The DatasetDTO
   */
  public Dataset(DatasetDTO data) {
    this(data.getName(), data.getDescription(), data.getDatapoints(), 
        data.getSize(), data.getUrl());
  }

  /**
   * Constructor for a Dataset.
   * 
   */
  public Dataset(String name, String description, String datapoints, double size, String url) {
    this.name = name;
    this.description = description;
    this.datapoints = datapoints;
    this.size = size;
    this.url = url;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDatapoints() {
    return datapoints;
  }

  public void setDatapoints(String datapoint) {
    this.datapoints = datapoint;
  }

  public double getSize() {
    return size;
  }

  public void setSize(double size) {
    this.size = size;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return String.format(
        "Dataset{id: '%s', name: '%s', description: '%s', datapoints: '%s', size: '%s', url: '%s'}",
        id, name, description, datapoints, size, url);
  }
}
