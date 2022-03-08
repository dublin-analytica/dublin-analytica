package ie.dublinanalytica.web.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A basic API response.
 */
public class Response {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Object data;

  public Response(Object data) {
    this.data = data;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

}
