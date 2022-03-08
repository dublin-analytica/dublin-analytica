package ie.dublinanalytica.web;

import ie.dublinanalytica.web.util.AuthUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        "Secret environment variable not set. Please add it to your .env file");
    }

    SpringApplication.run(WebApplication.class, args);
  }

}
