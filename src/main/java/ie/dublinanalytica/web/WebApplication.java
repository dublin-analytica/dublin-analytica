package ie.dublinanalytica.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ie.dublinanalytica.web.util.AuthUtils;

/**
 * WebApplication.
 */
@SpringBootApplication
public class WebApplication {

  /**
   * Application entry point.
   */
  public static void main(String[] args) {
    if (AuthUtils.getSecret() == null) {
      throw new RuntimeException(
        "Secret environment variable not set. Please add it to the .development.env file");
    }

    SpringApplication.run(WebApplication.class, args);
  }

}
