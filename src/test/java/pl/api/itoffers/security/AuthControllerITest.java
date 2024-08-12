package pl.api.itoffers.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AuthRequestBodyFactory;
import pl.api.itoffers.security.ui.controller.AuthController;

import pl.api.itoffers.security.ui.response.AuthResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerITest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void shouldGetAccessToken() throws Exception {
        ResponseEntity<AuthResponse> response = template.postForEntity(AuthController.GET_TOKEN_PATH, AuthRequestBodyFactory.create(), AuthResponse.class);

        assertThat(response.getBody().getToken()).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    // TODO write Request Body Payload validation tests
}
