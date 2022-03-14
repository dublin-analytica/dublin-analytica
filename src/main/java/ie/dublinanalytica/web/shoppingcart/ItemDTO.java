package ie.dublinanalytica.web.shoppingcart;

import java.util.HashSet;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Data-Transfer-Object to store the necessary information to create an item in shoppingcart.
 */
public class ItemDTO {

  @NotNull
  @NotEmpty
  private HashSet<Integer> datapoints;

  @NotNull
  @NotEmpty
  private UUID datasetId;

  public HashSet<Integer> getDatapoints() {
    return datapoints;
  }

  public void setDatapoints(HashSet<Integer> datapoints) {
    this.datapoints = datapoints;
  }

  public UUID getDatasetId() {
    return datasetId;
  }

  public void setDatasetId(UUID datasetId) {
    this.datasetId = datasetId;
  }

  @Override
  public String toString() {
    return String.format(
        "ItemDTO{id: '%s', datapoints: '%s'}",
        datasetId, datapoints);
  }

}
