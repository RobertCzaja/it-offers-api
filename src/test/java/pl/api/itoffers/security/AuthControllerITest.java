package pl.api.itoffers.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.security.ui.controller.AuthController;
import pl.api.itoffers.security.ui.response.AuthResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerITest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void shouldGetAccessToken() throws Exception {
        String email = "a@a.pl";
        String url = AuthController.GET_TOKEN_PATH+"?email="+email;

        ResponseEntity<String> response = template.getForEntity(url, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful());
    }
}
