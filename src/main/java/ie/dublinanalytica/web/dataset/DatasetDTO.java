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
  private Integer size;

  @NotNull
  @NotEmpty
  private String image;

  @NotNull
  private Double unitPrice;

  public DatasetDTO() {}

  /**
   * Constructor class for DatasetDTO.
   */
  public DatasetDTO(String name, String description, int size,
                    String image, double pricePerDatapoint) {
    this.name = name;
    this.description = description;
    this.size = size;
    this.image = image;
    this.unitPrice = pricePerDatapoint;
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

  public Double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Double unitPrice) {
    this.unitPrice = unitPrice;
  }

  @Override
  public String toString() {
    return String.format(
        "DatasetDTO{name: '%s', description: '%s', size: '%s', image: '%s'}",
        name, description, size, image);
  }

}
