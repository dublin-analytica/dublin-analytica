package ie.dublinanalytica.web.api.response;

import org.springframework.http.HttpStatus;

/**
 * A response with an empty object.
 */
public class EmptyResponse extends Response {
  public EmptyResponse(HttpStatus status) {
    super("{}", status);
  }
}
