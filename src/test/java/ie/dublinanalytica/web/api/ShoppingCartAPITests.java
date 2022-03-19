package ie.dublinanalytica.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.shoppingcart.ItemDTO;
import ie.dublinanalytica.web.user.RegistrationDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

  private final char[] PASSWORD = "admin".toCharArray();

  @Test
  @Order(1)
  public void confirmCheckoutShouldThrowExceptionIfCartIsEmpty() throws Exception {
    String token = getAuthToken();

    this.mockMvc.perform(
        post("/api/cart/checkout")
          .header("Authorization", "Bearer " + token))
      .andExpect(status().isBadRequest());
  }

  @Test
  @Order(2)
  public void confirmCheckoutShouldReturnOkIfSuccessful() throws Exception {
    String token = getAuthToken();
    String id = getId();

    this.mockMvc.perform(
      put("/api/cart/")
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new ItemDTO(UUID.fromString(id), 5))));

    this.mockMvc.perform(
        post("/api/cart/checkout")
          .header("Authorization", "Bearer " + token))
      .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  public void confirmCheckoutShouldBeUnauthorizedIfInvalidToken() throws Exception {
    this.mockMvc.perform(
        post("/api/cart/checkout")
          .header("Authorization", "Bearer INVALID TOKEN"))
      .andExpect(status().isUnauthorized());
  }

  @Test
  @Order(3)
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
  public void returnCartShouldBeUnauthorizedIfInvalidToken() throws Exception {
    this.mockMvc.perform(
        get("/api/cart/")
          .header("Authorization", "Bearer INVALID TOKEN"))
      .andDo(print())
      .andExpect(status().isUnauthorized());
  }

  @Test
  @Order(4)
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
  public void putToCart() throws Exception {
    String token = getAuthToken();
    String id = getId();

    this.mockMvc.perform(
        put("/api/cart/")
          .header("Authorization", "Bearer " + token)
          .contentType("application/json")
          .content(toJSON(new ItemDTO(UUID.fromString(id), 5))))
      .andExpect(status().isCreated());
  }

  @Test
  @Order(5)
  public void putToCartShouldBeUnauthorizedIfInvalidToken() throws Exception {
    String id = getId();

    this.mockMvc.perform(
        put("/api/cart/")
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
    String USERNAME = "Bob the Admin";
    String EMAIL = "admin@gmail.com";
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
   * Gets the id of a dataset
   */
  public String getId() throws Exception {
    MvcResult result = this.mockMvc.perform(
        get("/api/datasets/"))
      .andReturn();

    String response = result.getResponse().getContentAsString();
    return JsonPath.parse(response).read("$.[0].id");
  }
}
