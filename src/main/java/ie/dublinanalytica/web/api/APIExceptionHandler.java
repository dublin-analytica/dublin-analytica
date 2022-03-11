package ie.dublinanalytica.web.api;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import ie.dublinanalytica.web.api.response.ErrorResponse;
import ie.dublinanalytica.web.exceptions.BaseException;

/**
 * Exception handler for exceptions thrown in the api methods.
 * Responsible for converting any exceptions thrown into a response with js representation
 * of the error
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  protected ErrorResponse handle(Exception e, WebRequest request) {
    if (e instanceof BaseException) {
      return new ErrorResponse((BaseException) e);
    }

    return new ErrorResponse(e.getClass().getSimpleName(), e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, @Nullable Object body, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    return new ErrorResponse("BadRequestException", ex.getMessage(), status);
  }
}
