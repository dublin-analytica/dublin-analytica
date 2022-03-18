package ie.dublinanalytica.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.orders.ChangeStatusDTO;
import ie.dublinanalytica.web.orders.Order;
import ie.dublinanalytica.web.user.RegistrationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

  @Test
  public void returnAllOrders() throws Exception {
    this.mockMvc.perform(
        get("/api/order/"))
      .andDo(print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.[0].status").value("PROCESSING"))
      .andExpect(status().isOk())
      .andReturn();
  }

  @Test
  public void returnOrder() throws Exception {
    String url = getOrderUrl();

    this.mockMvc.perform(
        get(url))
      .andDo(print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PLACED"))
      .andExpect(status().isOk())
      .andReturn();
  }

  @Test
  public void returnExceptionIfIncorrectOrderId() throws Exception {
    this.mockMvc.perform(
        get("/api/order/9af-e9468f4785f3"))
      .andDo(print())
      .andExpect(status().isBadRequest())
      .andReturn();
  }

  @Test
  public void updateOrderStatus() throws Exception {
    String token = getAuthToken();
    String url = getOrderUrl() + "/status";

    this.mockMvc.perform(
      post(url)
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new ChangeStatusDTO(Order.OrderStatus.SHIPPED))))
        .andDo(print())
        .andExpect(status().isOk());

    this.mockMvc.perform(
        get("/api/order/"))
      .andDo(print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.[1].status").value("SHIPPED"))
      .andExpect(status().isOk())
      .andReturn();
  }

  @Test
  public void updateOrderStatusShouldBeUnauthorizedIfInvalidToken() throws Exception {
    String url = getOrderUrl() + "/status";

    this.mockMvc.perform(
        post(url)
          .header("Authorization", "Bearer INVALID TOKEN")
          .contentType("application/json")
          .content(toJSON(new ChangeStatusDTO(Order.OrderStatus.SHIPPED)))
      ).andDo(print())
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
   * Gets the url for a specific order
   */
  public String getOrderUrl() throws Exception {
    MvcResult result = this.mockMvc.perform(
        get("/api/order/"))
      .andReturn();

    String response = result.getResponse().getContentAsString();
    String id = JsonPath.parse(response).read("$.[1].id");
    return "/api/order/" + id;
  }
}
