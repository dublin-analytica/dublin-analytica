package ie.dublinanalytica.web.shoppingcart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Data-Transfer-Object to store the necessary information to verify a card.
 */
@Valid
public class CardDTO {

  @NotNull
  private String cvv;

  @NotNull
  private String expiry;

  @NotNull
  private String cardNum;

  public CardDTO() {}

  /**
   * Constructor for CardDTO.
   */
  public CardDTO(String cvv, String expiry, String cardNum) {
    this.cvv = cvv;
    this.expiry = expiry;
    this.cardNum = cardNum;
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

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }
}
