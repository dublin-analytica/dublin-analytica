package ie.dublinanalytica.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Tests for the /api/order endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderAPITests {

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

}
