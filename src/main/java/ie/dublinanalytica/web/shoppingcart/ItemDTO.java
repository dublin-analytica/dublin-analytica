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
  private UUID datasetId;

  @NotNull
  private int datapointCount;

  public ItemDTO() {}

  public ItemDTO(UUID datasetId, int datapointCount) {
    this.datasetId = datasetId;
    this.datapointCount = datapointCount;
  }

  public UUID getDatasetId() {
    return this.datasetId;
  }

  public void setDatasetId(UUID datasetId) {
    this.datasetId = datasetId;
  }

  public int getDatapointCount() {
    return this.datapointCount;
  }

  public void setDatapointCount(int datapointCount) {
    this.datapointCount = datapointCount;
  }

  @Override
  public String toString() {
    return String.format(
        "ItemDTO{id: '%s', datapointCount: '%s'}",
        datasetId, datapointCount);
  }

}
