package ie.dublinanalytica.web.shoppingcart;

import java.util.UUID;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Data-Transfer-Object to store the necessary information to create an item in shoppingcart.
 */
@Embeddable
@Valid
public class ItemDTO {

  @NotNull
  private UUID id;

  @NotNull
  private int size;

  public ItemDTO() {}

  public ItemDTO(UUID id, int datapointCount) {
    this.id = id;
    this.size = datapointCount;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID datasetId) {
    this.id = datasetId;
  }

  public int getSize() {
    return this.size;
  }

  public void setSize(int datapointCount) {
    this.size = datapointCount;
  }

  @Override
  public String toString() {
    return String.format(
        "ItemDTO{id: '%s', datapointCount: '%s'}",
      id, size);
  }

}
