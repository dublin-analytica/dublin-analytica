package ie.dublinanalytica.web.dataset;

import java.util.List;

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

  private List<String> datapoints;

  @NotNull
  private Integer size;

  @NotNull
  @NotEmpty
  private String image;

  @NotNull
  private Boolean hidden;

  @NotNull
  private Double pricePerDatapoint;

  public DatasetDTO() {}

  /**
   * Constructor class for DatasetDTO.
   */
  public DatasetDTO(String name, String description, List<String> datapoints, int size,
                    String image, double pricePerDatapoint) {
    this.name = name;
    this.description = description;
    this.datapoints = datapoints;
    this.size = size;
    this.image = image;
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

  public List<String> getDatapoints() {
    return datapoints;
  }

  public void setDatapoints(List<String> datapoints) {
    this.datapoints = datapoints;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
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
        "DatasetDTO{name: '%s', description: '%s', datapoints: '%s', size: '%s', image: '%s'}",
        name, description, datapoints, size, image);
  }

}
