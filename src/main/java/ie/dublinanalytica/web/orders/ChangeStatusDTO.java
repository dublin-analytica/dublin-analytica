package ie.dublinanalytica.web.orders;

import javax.validation.constraints.NotNull;

/**
 * DTO to change the status of an order.
 */
public class ChangeStatusDTO {

  @NotNull
  public Order.OrderStatus status;

  public ChangeStatusDTO() {

  }

  public ChangeStatusDTO(Order.OrderStatus status) {
    this.status = status;
  }

  public Order.OrderStatus getStatus() {
    return status;
  }

  public void setStatus(Order.OrderStatus status) {
    this.status = status;
  }
}
