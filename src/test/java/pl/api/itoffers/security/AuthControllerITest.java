package pl.api.itoffers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.security.ui.controller.AuthController;

import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;
import pl.api.itoffers.security.ui.response.AuthResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerITest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void shouldGetAccessToken() throws Exception {
        URI uri = UriComponentsBuilder
                .fromUriString(AuthController.GET_TOKEN_PATH)
                .queryParam("email", "a@a.pl")
                .build()
                .toUri();

        ResponseEntity<AuthResponse> response = template.getForEntity(uri, AuthResponse.class);

        assertThat(response.getBody().getToken()).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    public void shouldGetErrorResponseOnLackOfEmail() {
        ResponseEntity<AuthResponse> response = template.getForEntity(AuthController.GET_TOKEN_PATH, AuthResponse.class);
        assertThat(response.getStatusCode().isError()).isTrue();
    }
}
