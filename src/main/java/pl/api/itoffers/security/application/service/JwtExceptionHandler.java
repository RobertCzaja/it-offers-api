package pl.api.itoffers.security.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import pl.api.itoffers.shared.http.exception.ValidationException;

@Component
public class JwtExceptionHandler {

  private final ObjectMapper mapper;

  public JwtExceptionHandler(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public void handle(Exception e, HttpServletResponse response) throws IOException {
    Map<String, Object> errorDetails = new HashMap<>();

    int httpStatusCode = HttpStatus.FORBIDDEN.value();
    String errorMessage = "authentication_error";
    String errorDetail = e.getMessage();
    if (e.getCause() instanceof ValidationException) {
      ValidationException validationException = (ValidationException) e.getCause();
      httpStatusCode = validationException.getHttpStatus().value();
      errorMessage = "validation_error";
      errorDetail = validationException.getMessage();
    } else if (e instanceof ExpiredJwtException) {
      httpStatusCode = HttpStatus.UNAUTHORIZED.value();
      errorMessage = "authorization_error";
      errorDetail = "Invalid token";
    }

    errorDetails.put("code", errorMessage);
    errorDetails.put("details", errorDetail);
    response.setStatus(httpStatusCode);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    mapper.writeValue(response.getWriter(), errorDetails);
  }
}
