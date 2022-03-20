package ie.dublinanalytica.web.dataset;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import ie.dublinanalytica.web.util.Utils;

/**
 * Class for storing a dataset.
 */
@Entity
@JsonIgnoreProperties(value = {"link"})
public class Dataset {
  @Id
  @GeneratedValue
  private UUID id;

  private String name;
  private String description;

  private int size;
  private String image;
  private String link;

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
    this(data.getName(),
        data.getDescription(), Utils.getSize(data.getLink()), data.getImage(), data.getImage());
  }

  /**
   * Constructor for a Dataset.
   *
   */
  public Dataset(String name, String description, int size, String image, String link) {
    this.name = name;
    this.description = description;
    this.size = size;
    this.image = image;
    this.link = link;
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

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return String.format(
      "Dataset{id: '%s', name: '%s', description: '%s' size: '%s', image: '%s'}",
        id, name, description, size, image);
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

  public String getLink() {
    return this.link;
  }

  public void setLink(String url) {
    this.link = url;
  }


  /**
   * Generates the json for admins. We need this to expose the link for admins only.
   *
   * @return The JSON String
   * @throws JsonProcessingException Error jackson
   */
  public String toJSONAdmin() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    ObjectNode user = mapper.createObjectNode();

    user.put("id", this.getId().toString());
    user.put("name", this.getName());
    user.put("description", this.getDescription());
    user.put("size", this.getSize());
    user.put("image", this.getImage());
    user.put("hidden", this.isHidden());
    user.put("link", this.getLink());
    user.put("unitPrice", this.getUnitPrice());

    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
  }

  public String fetchFile() {
    return Utils.fetchFile(this.getLink());
  }
}
