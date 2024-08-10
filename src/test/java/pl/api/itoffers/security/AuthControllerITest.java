package pl.api.itoffers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.security.ui.controller.AuthController;

import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.shell.interactive.enabled=false"}
)
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

        ResponseEntity<String> response = template.getForEntity(uri, String.class);

        Map<String, Object> responseMap = new ObjectMapper().readValue(response.getBody(), HashMap.class);

        assertThat(responseMap.get("token")).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    public void shouldGetErrorResponseOnLackOfEmail() {
        ResponseEntity<String> response = template.getForEntity(AuthController.GET_TOKEN_PATH, String.class);
        assertThat(response.getStatusCode().isError()).isTrue();
    }
}
