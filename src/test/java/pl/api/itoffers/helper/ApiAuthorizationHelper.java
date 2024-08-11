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

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApiAuthorizationHelper {

    @Autowired
    private TestRestTemplate template;

    public HttpHeaders getHeaders() throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString(AuthController.GET_TOKEN_PATH)
                .queryParam("email", "a@a.pl")
                .build()
                .toUri();

        ResponseEntity<String> authResponse = template.getForEntity(uri, String.class);

        Map<String, Object> responseMap = new ObjectMapper().readValue(authResponse.getBody(), HashMap.class);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+responseMap.get("token"));

        return headers;
    }
}
