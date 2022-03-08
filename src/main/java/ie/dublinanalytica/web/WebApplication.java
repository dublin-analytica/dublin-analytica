package ie.dublinanalytica.web;

import ie.dublinanalytica.web.util.AuthUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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

    ConfigurableApplicationContext ctx = SpringApplication.run(WebApplication.class, args);
    ctx.close();
  }

}
