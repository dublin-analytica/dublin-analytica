package ie.dublinanalytica.web.dataset;

import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class for storing a dataset.
 */
@Entity
@JsonIgnoreProperties(value = {"image"})
public class Dataset {
  @Id
  @GeneratedValue
  private UUID id;

  private String name;
  private String description;

  @ElementCollection
  private List<String> fields;

  private int size;
  private String image;

  private boolean hidden;
  private double unitPrice;

  public Dataset() {
  }

  /**
   * Creates a Dataset from DatasetDTO.
   *
   * @param data The DatasetDTO
   */
  public Dataset(DatasetDTO data) {
    this(data.getName(), data.getDescription(), data.getDatapoints(),
        data.getSize(), data.getImage());
  }

  /**
   * Constructor for a Dataset.
   *
   */
  public Dataset(String name, String description, List<String> fields, int size, String image) {
    this.name = name;
    this.description = description;
    this.fields = fields;
    this.size = size;
    this.image = image;
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

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> datapoint) {
    this.fields = datapoint;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return String.format(
      "Dataset{id: '%s', name: '%s', description: '%s', datapoints: '%s', size: '%s', image: '%s'}",
        id, name, description, fields, size, image);
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double pricePerDatapoint) {
    this.unitPrice = pricePerDatapoint;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getImage() {
    return image;
  }
}
