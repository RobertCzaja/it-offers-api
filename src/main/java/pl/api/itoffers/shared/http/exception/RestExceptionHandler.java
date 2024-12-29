package pl.api.itoffers.shared.http.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Object> handleValidationException(ValidationException ex) {
    return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), ex.getHttpStatus());
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    log.warn("Error HTTP response {}: {}", ex.getClass().getName(), ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse("Bad payload request!"), HttpStatus.BAD_REQUEST);
  }
}
