package ie.dublinanalytica.web.dataset;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Data-Transfer-Object to store the necessary information to create a dataset.
 */
public class DatasetDTO {

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  @NotEmpty
  private String description;

  @NotNull
  @NotEmpty
  private String datapoints;

  @NotNull
  private Double size;

  @NotNull
  @NotEmpty
  private String url;

  @NotNull
  private Boolean hidden;

  @NotNull
  private Double pricePerDatapoint;

  public DatasetDTO() {}

  /**
   * Constructor class for DatasetDTO.
   */
  public DatasetDTO(String name, String description, String datapoints, double size, String url, double pricePerDatapoint) {
    this.name = name;
    this.description = description;
    this.datapoints = datapoints;
    this.size = size;
    this.url = url;
    this.pricePerDatapoint = pricePerDatapoint;
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

  public void setDatapoints(String datapoints) {
    this.datapoints = datapoints;
  }

  public Double getSize() {
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

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public Boolean isHidden() {
    return hidden;
  }

  public Double getPricePerDatapoint() {
    return pricePerDatapoint;
  }

  public void setPricePerDatapoint(Double pricePerDatapoint) {
    this.pricePerDatapoint = pricePerDatapoint;
  }

  @Override
  public String toString() {
    return String.format(
        "DatasetDTO{name: '%s', description: '%s', datapoints: '%s', size: '%s', url: '%s'}",
        name, description, datapoints, size, url);
  }

}
