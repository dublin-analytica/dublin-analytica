package ie.dublinanalytica.web.api;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.shoppingcart.CardDTO;
import ie.dublinanalytica.web.shoppingcart.ItemDTO;
import ie.dublinanalytica.web.user.RegistrationDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Tests for the /api/cart endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingCartAPITests {

  @Autowired
  private MockMvc mockMvc;

  private final char[] PASSWORD = "Alice&bob1".toCharArray();

  @Test
  @Order(1)
  @Rollback
  public void confirmCheckoutShouldThrowExceptionIfCartIsEmpty() throws Exception {
    String token = getAuthToken();

    this.mockMvc.perform(
        post("/api/cart/checkout")
          .header("Authorization", "Bearer " + token))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(2)
  @Rollback
  public void confirmCheckoutShouldReturnOkIfSuccessful() throws Exception {
    String token = getAuthToken();
    String id = getId();

    this.mockMvc.perform(
        patch("/api/cart/")
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new ItemDTO(UUID.fromString(id), 5))));

    this.mockMvc.perform(
        post("/api/cart/checkout")
          .header("Authorization", "Bearer " + token)
          .contentType("application/json")
          .content(toJSON(new CardDTO("233", "12/22", "4829912773565293"))))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  @Rollback
  public void confirmCheckoutShouldThrowExceptionIfCardNumberIsInvalid() throws Exception {
    String token = getAuthToken();
    String id = getId();

    this.mockMvc.perform(
        put("/api/cart/")
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new ItemDTO(UUID.fromString(id), 5))));

    this.mockMvc.perform(
        post("/api/cart/checkout")
          .header("Authorization", "Bearer " + token)
          .contentType("application/json")
          .content(toJSON(new CardDTO("233", "12/22", "4444"))))
        .andExpect(status().isInternalServerError());
  }

  @Test
  @Order(2)
  @Rollback
  public void confirmCheckoutShouldThrowExceptionIfExpiryDateIsInvalid() throws Exception {
    String token = getAuthToken();
    String id = getId();

    this.mockMvc.perform(
        put("/api/cart/")
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new ItemDTO(UUID.fromString(id), 5))));

    this.mockMvc.perform(
        post("/api/cart/checkout")
          .header("Authorization", "Bearer " + token)
          .contentType("application/json")
          .content(toJSON(new CardDTO("233", "12/19", "4444"))))
        .andExpect(status().isInternalServerError());
  }

  @Test
  @Order(2)
  @Rollback
  public void confirmCheckoutShouldThrowExceptionIfCvvIsInvalid() throws Exception {
    String token = getAuthToken();
    String id = getId();

    this.mockMvc.perform(
        put("/api/cart/")
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new ItemDTO(UUID.fromString(id), 5))));

    this.mockMvc.perform(
        post("/api/cart/checkout")
          .header("Authorization", "Bearer " + token)
          .contentType("application/json")
          .content(toJSON(new CardDTO("23377", "12/19", "4444"))))
        .andExpect(status().isInternalServerError());
  }

  @Test
  @Order(2)
  @Rollback
  public void confirmCheckoutShouldBeUnauthorizedIfInvalidToken() throws Exception {
    this.mockMvc.perform(
        post("/api/cart/checkout")
          .header("Authorization", "Bearer INVALID TOKEN")
          .contentType("application/json")
          .content(toJSON(new CardDTO("233", "12/22", "4829912773565293"))))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @Order(3)
  @Rollback
  public void returnCart() throws Exception {
    String token = getAuthToken();

    this.mockMvc.perform(
      get("/api/cart/")
        .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  @Rollback
  public void returnCartShouldBeUnauthorizedIfInvalidToken() throws Exception {
    this.mockMvc.perform(
        get("/api/cart/")
          .header("Authorization", "Bearer INVALID TOKEN"))
      .andDo(print())
        .andExpect(status().isUnauthorized());
  }

  @Test
  @Order(4)
  @Rollback
  public void addToCart() throws Exception {
    String token = getAuthToken();
    String id = getId();

    this.mockMvc.perform(
      post("/api/cart/")
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new ItemDTO(UUID.fromString(id), 5))))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(4)
  @Rollback
  public void addToCartShouldBeUnauthorizedIfInvalidToken() throws Exception {
    String id = getId();

    this.mockMvc.perform(
        post("/api/cart/")
          .header("Authorization", "Bearer INVALID TOKEN")
          .contentType("application/json")
          .content(toJSON(new ItemDTO(UUID.fromString(id), 5))))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @Order(5)
  @Rollback
  public void putToCart() throws Exception {
    String token = getAuthToken();
    String id = getId();

    this.mockMvc.perform(
        patch("/api/cart/")
          .header("Authorization", "Bearer " + token)
          .contentType("application/json")
          .content(toJSON(new ItemDTO(UUID.fromString(id), 5))))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(5)
  @Rollback
  public void putToCartShouldBeUnauthorizedIfInvalidToken() throws Exception {
    String id = getId();

    this.mockMvc.perform(
        patch("/api/cart/")
          .header("Authorization", "Bearer INVALID TOKEN")
          .contentType("application/json")
          .content(toJSON(new ItemDTO(UUID.fromString(id), 5))))
        .andExpect(status().isUnauthorized());
  }

  /**
   * Converts an object to JSON using jackson.
   *
   * @param o the object to convert
   * @return the JSON string
   */
  public static String toJSON(Object o) {
    try {
      return (new ObjectMapper()).writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to convert object to JSON: " + o, e);
    }
  }

  /**
   * Gets the Auth token associated with a given user.
   */
  public String getAuthToken() throws Exception {
    final String USERNAME = "Bob the Admin";
    final String EMAIL = "alice@gmail.com";
    MvcResult result = this.mockMvc.perform(
        post("/api/users/login")
        .contentType("application/json")
        .content(toJSON(new RegistrationDTO(USERNAME, EMAIL, PASSWORD)))
    ).andReturn();

    AuthResponse.AuthObject response = (new ObjectMapper())
        .readValue(result.getResponse().getContentAsByteArray(), AuthResponse.AuthObject.class);

    return response.token();
  }

  /**
   * Gets the id of a dataset.
   */
  public String getId() throws Exception {
    MvcResult result = this.mockMvc.perform(
        get("/api/datasets/"))
        .andReturn();

    String response = result.getResponse().getContentAsString();
    return JsonPath.parse(response).read("$.[0].id");
  }
}
