package ie.dublinanalytica.web.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Configuration class for the logging filter.
 * Log all requests with headers and body
 */
@Configuration
public class RequestLoggingConfig {

  /**
   * Creates the logging filter.
   *
   * @return The created filter
   */
  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(10000);
    filter.setIncludeHeaders(true);
    return filter;
  }
}
