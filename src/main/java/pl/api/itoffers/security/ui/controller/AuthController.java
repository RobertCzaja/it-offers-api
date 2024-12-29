package pl.api.itoffers.security.ui.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.security.application.service.AuthorizationService;
import pl.api.itoffers.security.domain.exception.UserNotFound;
import pl.api.itoffers.security.ui.request.AuthorizationRequest;
import pl.api.itoffers.security.ui.response.AuthResponse;

@RestController
public class AuthController {

  public static final String GET_TOKEN_PATH = "/auth";

  @Autowired private AuthorizationService authorizationService;

  @PostMapping(AuthController.GET_TOKEN_PATH)
  public ResponseEntity<AuthResponse> auth(@Valid @RequestBody AuthorizationRequest request) {
    try {
      String token = authorizationService.getToken(request.getEmail(), request.getPassword());

      if (null == token) {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
      }

      AuthResponse response = new AuthResponse();
      response.setToken(token);

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (UserNotFound e) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }
}
