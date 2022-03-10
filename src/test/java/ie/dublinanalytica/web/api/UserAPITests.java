package ie.dublinanalytica.web.api;


import java.util.UUID;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import ie.dublinanalytica.web.api.response.AuthResponse;
import ie.dublinanalytica.web.user.AuthDTO;
import ie.dublinanalytica.web.user.RegistrationDTO;
import ie.dublinanalytica.web.util.AuthUtils;

import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserAPITests {

  private final static String ERROR_PATTERN = "^\\{\"name\":\".*\",\"message\":\".*\"}$";
  private final static String NAME = "Alice Bob";
  private final static String EMAIL = "alice@bob.com";
  private final static char[] PASSWORD = "alice&bob".toCharArray();

  @Autowired
  private MockMvc mockMvc;

  public static String toJSON(Object o) {
    try {
      return (new ObjectMapper()).writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to convert object to JSON: " + o, e);
    }
  }

  @Test
  @Order(1)
  public void registerShouldReturnCreatedIfSuccessful() throws Exception {
    this.mockMvc.perform(
        post("/api/users/register")
          .contentType("application/json")
          .content(toJSON(new RegistrationDTO(NAME, EMAIL, PASSWORD))))
      .andDo(print())
      .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  public void registerShouldReturnInternalServerErrorIfEmailIsInUse() throws Exception {
    this.mockMvc.perform(
        post("/api/users/register")
          .contentType("application/json")
          .content(toJSON(new RegistrationDTO(NAME, EMAIL, PASSWORD))))
      .andDo(print())
      .andExpect(status().isInternalServerError());
  }

  @Test
  @Order(2)
  public void registerShouldReturnErrorObjectIfEmailIsInUse() throws Exception {
    this.mockMvc.perform(
        post("/api/users/register")
          .contentType("application/json")
          .content(toJSON(new RegistrationDTO(NAME, EMAIL, PASSWORD))))
      .andDo(print())
      .andExpect(status().isInternalServerError())
      .andExpect(content().string(
        matchesRegex(ERROR_PATTERN)
      ));
  }

  @Test
  @Order(3)
  public void loginShouldReturnOkIFSuccessful() throws Exception {
    this.mockMvc.perform(
        post("/api/users/login")
          .contentType("application/json")
          .content(toJSON(new AuthDTO(EMAIL, PASSWORD))))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  public void loginShouldReturnErrorObjectIfFailure() throws Exception {
    this.mockMvc.perform(
        post("/api/users/login")
          .contentType("application/json")
          .content(toJSON(new RegistrationDTO(NAME, EMAIL, ("wrong" + new String(PASSWORD)).toCharArray()))))
      .andDo(print())
      .andExpect(status().isUnauthorized())
      .andExpect(content().string(matchesRegex(ERROR_PATTERN)));
  }

  @Test
  @Order(4)
  public void loginShouldReturnOkIfSuccessful() throws Exception {
    this.mockMvc.perform(
        post("/api/users/login")
          .contentType("application/json")
          .content(toJSON(new AuthDTO(EMAIL, PASSWORD))))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  @Order(4)
  public void loginShouldReturnAuthResponseIfSuccessful() throws Exception {
    MvcResult result = this.mockMvc.perform(
        post("/api/users/login")
          .contentType("application/json")
          .content(toJSON(new AuthDTO(EMAIL, PASSWORD))))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(matchesRegex("^\\{\"jwt\":\".*\"}$")))
      .andReturn();

    AuthResponse response =
      (new ObjectMapper()).readValue(result.getResponse().getContentAsByteArray(), AuthResponse.class);

    assertNotNull(response.getJWT(), "JWT should not be null");
  }

  @Test
  @Order(4)
  public void loginShouldReturnValidTokenIfSuccessful() throws Exception {
    MvcResult result = this.mockMvc.perform(
        post("/api/users/login")
          .contentType("application/json")
          .content(toJSON(new AuthDTO(EMAIL, PASSWORD))))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(matchesRegex("^\\{\"jwt\":\".*\"}$")))
      .andReturn();

    AuthResponse response =
      (new ObjectMapper()).readValue(result.getResponse().getContentAsByteArray(), AuthResponse.class);

    String token = response.getJWT();

    DecodedJWT jwt = AuthUtils.decodeJwtToken(token);
  }

  @Test
  @Order(4)
  public void loginShouldReturnTokenWithUserDataIfSuccessful() throws Exception {
    MvcResult result = this.mockMvc.perform(
        post("/api/users/login")
          .contentType("application/json")
          .content(toJSON(new AuthDTO(EMAIL, PASSWORD))))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(matchesRegex("^\\{\"jwt\":\".*\"}$")))
      .andReturn();

    AuthResponse response =
      (new ObjectMapper()).readValue(result.getResponse().getContentAsByteArray(), AuthResponse.class);

    String token = response.getJWT();

    DecodedJWT jwt = AuthUtils.decodeJwtToken(token);

    String idString = jwt.getClaim("id").asString();
    assertNotNull(idString, "JWT ID claim should not be null");

    try {
      UUID uuid = UUID.fromString(idString);
    } catch (IllegalArgumentException e) {
      fail("JWT ID claim should be a valid UUID");
    }

    String name = jwt.getClaim("name").asString();
    assertNotNull(name, "JWT name claim should not be null");

    String email = jwt.getClaim("email").asString();
    assertNotNull(email, "JWT email claim should not be null");
  }

  @Test
  @Order(5)
  public void logoutShouldReturnOkIfSuccessful() throws Exception {
    MvcResult result = this.mockMvc.perform(
        post("/api/users/login")
          .contentType("application/json")
          .content(toJSON(new AuthDTO(EMAIL, PASSWORD))))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(matchesRegex("^\\{\"jwt\":\".*\"}$")))
      .andReturn();

    AuthResponse response =
      (new ObjectMapper()).readValue(result.getResponse().getContentAsByteArray(), AuthResponse.class);

    String token = response.getJWT();

    this.mockMvc.perform(
        post("/api/users/logout").header("Authorization", "Bearer " + token))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  @Order(5)
  public void logoutShouldReturnUnauthorizedIfNoToken() throws Exception {
    this.mockMvc.perform(
        post("/api/users/logout").header("Authorization", "Bearer INVALID TOKEN"))
      .andDo(print())
      .andExpect(status().isUnauthorized());
  }

  @Test
  @Order(5)
  public void logoutShouldReturnBadRequestIfMalformedAuthorizationHeader() throws Exception {
    this.mockMvc.perform(
        post("/api/users/logout"))
      .andDo(print())
      .andExpect(status().isBadRequest());

    this.mockMvc.perform(
        post("/api/users/logout").header("Authorization", "NotBearer TOKEN"))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  // TODO: Add tests to verify that auth tokens can actually be used. Not done for /users/me in case it gets removed
}
