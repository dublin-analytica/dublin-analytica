package ie.dublinanalytica.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jayway.jsonpath.JsonPath;
import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.dataset.DatasetDTO;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for the /api/dataset endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatasetAPITests {

  private final String NAME = "Dataset1";
  private final String DESCRIPTION = "A dataset";
  private final String DATAPOINTS = "alice, bob";
  private final double SIZE = 1.0;
  private final String URL = "https://helloworld.com";
  private final String USERNAME = "Bob the Admin";
  private final String EMAIL = "admin@gmail.com";
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

  public String getAuthToken() throws Exception {
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
  @Order(1)
  public void createShouldReturnCreatedIfSuccessful() throws Exception {
    String token = getAuthToken();
    this.mockMvc.perform(
      post("/api/dataset/create")
        .header("Authorization", "Bearer " + token)
        .contentType("application/json")
        .content(toJSON(new DatasetDTO(NAME, DESCRIPTION, DATAPOINTS, SIZE, URL)))
    ).andDo(print())
      .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  public void returnAllDatasets() throws Exception {
    this.mockMvc.perform(
      get("/api/dataset/"))
      .andDo(print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Some dataset"))
      .andExpect(status().isOk())
      .andReturn();
  }

  @Test
  @Order(2)
  public void returnDataset() throws Exception {
    MvcResult result = this.mockMvc.perform(
        get("/api/dataset/"))
      .andReturn();

    String response = result.getResponse().getContentAsString();
    String id = JsonPath.parse(response).read("$.[0].id");
    String url = "/api/dataset/" + id;

    this.mockMvc.perform(
      get(url))
      .andDo(print())
      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Some dataset"))
      .andExpect(status().isOk())
      .andReturn();
  }
}
