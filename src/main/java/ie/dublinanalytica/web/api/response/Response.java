package ie.dublinanalytica.web.api.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * A basic response object wrapper around ResponseEntity for cleaner code.
 * ResponseEntity will convert the body to JSON automatically.
 */
public class Response extends ResponseEntity<Object> {
  public Response(Object body, HttpStatus status) {
    super(body, status);
  }

  public Response(Object body, MediaType contentType) {
    super(body, createHeaders(contentType), HttpStatus.OK);
  }

  public Response(Object body) {
    this(body, HttpStatus.OK);
  }

  private static HttpHeaders createHeaders(MediaType contentType) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(contentType);
    return headers;
  }
}
