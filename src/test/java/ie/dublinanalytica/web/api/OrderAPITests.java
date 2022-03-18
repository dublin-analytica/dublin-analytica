package ie.dublinanalytica.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.orders.ChangeStatusDTO;
import ie.dublinanalytica.web.orders.Order;
import ie.dublinanalytica.web.user.RegistrationDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for the /api/order endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrderAPITests {

  private final char[] PASSWORD = "admin".toCharArray();

  @Autowired
  private MockMvc mockMvc;

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

  @Test
  public void returnAllOrders() throws Exception {
    String authToken = getAuthToken();

    this.mockMvc.perform(
        get("/api/orders/").header("Authorization", "Bearer " + authToken))
      .andDo(print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.[0].status").value("PROCESSING"))
      .andExpect(status().isOk())
      .andReturn();
  }

  @Test
  public void returnOrder() throws Exception {
    String authToken = getAuthToken();

    MvcResult result = this.mockMvc.perform(
        get("/api/orders/").header("Authorization", "Bearer " + authToken))
      .andReturn();

    String response = result.getResponse().getContentAsString();
    String id = JsonPath.parse(response).read("$.[1].id");
    String url = "/api/orders/" + id;

    this.mockMvc.perform(
        get(url).header("Authorization", "Bearer " + authToken))
      .andDo(print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PLACED"))
      .andExpect(status().isOk())
      .andReturn();
  }

  @Test
  public void returnExceptionIfIncorrectOrderId() throws Exception {
    this.mockMvc.perform(
        get("/api/orders/9af-e9468f4785f3"))
      .andDo(print())
      .andExpect(status().isBadRequest())
      .andReturn();
  }

  @Test
  public void updateOrderStatus() throws Exception {
    String token = getAuthToken();
    MvcResult result = this.mockMvc.perform(
        get("/api/orders/").header("Authorization", "Bearer " + token))
      .andReturn();

    String response = result.getResponse().getContentAsString();
    String id = JsonPath.parse(response).read("$.[1].id");
    String url = "/api/orders/" + id + "/status";

    this.mockMvc.perform(
      post(url)
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new ChangeStatusDTO(Order.OrderStatus.DELIVERED))))
        .andDo(print())
        .andExpect(status().isOk());

    this.mockMvc.perform(
        get("/api/orders/" + id).header("Authorization", "Bearer " + token))
      .andDo(print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("DELIVERED"))
      .andExpect(status().isOk())
      .andReturn();
  }

  @Test
  public void updateOrderStatusShouldBeUnauthorizedIfInvalidToken() throws Exception {
    String authToken = getAuthToken();

    MvcResult result = this.mockMvc.perform(
        get("/api/orders/").header("Authorization", "Bearer " + authToken))
      .andReturn();

    String response = result.getResponse().getContentAsString();
    String id = JsonPath.parse(response).read("$.[1].id");
    String url = "/api/orders/" + id + "/status";

    this.mockMvc.perform(
        post(url)
          .header("Authorization", "Bearer INVALID TOKEN")
          .contentType("application/json")
          .content(toJSON(new ChangeStatusDTO(Order.OrderStatus.DELIVERED)))
      ).andDo(print())
      .andExpect(status().isUnauthorized());
  }
}
