package ie.dublinanalytica.web.shoppingcart;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Data-Transfer-Object to store the necessary information to verify a card.
 */
@Valid
public class CardDTO {

  @NotNull
  @NotEmpty
  private String cvv;

  @NotNull
  @NotEmpty
  private String expiry;

  @NotNull
  @NotEmpty
  private String number;

  public CardDTO() {}

  /**
   * Constructor for CardDTO.
   */
  public CardDTO(String cvv, String expiry, String cardNum) {
    this.cvv = cvv;
    this.expiry = expiry;
    this.number = cardNum;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}
