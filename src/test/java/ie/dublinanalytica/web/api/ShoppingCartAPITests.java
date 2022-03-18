package ie.dublinanalytica.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.orders.ChangeStatusDTO;
import ie.dublinanalytica.web.orders.Order;
import ie.dublinanalytica.web.shoppingcart.ItemDTO;
import ie.dublinanalytica.web.user.RegistrationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Tests for the /api/cart endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartAPITests {

  @Autowired
  private MockMvc mockMvc;

  private final char[] PASSWORD = "admin".toCharArray();

  @Test
  public void returnCart() throws Exception {
    String token = getAuthToken();

    this.mockMvc.perform(
      get("/api/cart/")
        .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void returnCartShouldBeUnauthorizedIfInvalidToken() throws Exception {
    this.mockMvc.perform(
        get("/api/cart/")
          .header("Authorization", "Bearer INVALID TOKEN"))
      .andDo(print())
      .andExpect(status().isUnauthorized());
  }

  @Test
  public void addToCart() throws Exception {
    String token = getAuthToken();

    MvcResult result = this.mockMvc.perform(
        get("/api/dataset/"))
      .andReturn();

    String response = result.getResponse().getContentAsString();
    String id = JsonPath.parse(response).read("$.[0].id");


    this.mockMvc.perform(
      post("/api/cart/")
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new ItemDTO(UUID.fromString(id), 5))))
      .andDo(print())
      .andExpect(status().isCreated());
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
}
