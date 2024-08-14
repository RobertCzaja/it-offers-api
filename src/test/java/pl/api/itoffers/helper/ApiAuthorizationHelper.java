package pl.api.itoffers.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import pl.api.itoffers.security.ui.controller.AuthController;
import pl.api.itoffers.security.ui.response.AuthResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApiAuthorizationHelper {

    @Autowired
    private TestRestTemplate template;

    public HttpHeaders getHeaders(AuthorizationCredentials credentials) throws JsonProcessingException {
        ResponseEntity<AuthResponse> response = template.postForEntity(
                AuthController.GET_TOKEN_PATH,
                AuthRequestBodyFactory.create(credentials),
                AuthResponse.class
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+response.getBody().getToken());

        return headers;
    }
}
