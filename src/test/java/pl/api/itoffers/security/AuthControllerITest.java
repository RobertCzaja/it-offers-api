package pl.api.itoffers.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
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
    public void shouldGetAccessToken() {
        ResponseEntity<AuthResponse> response = template.postForEntity(AuthController.GET_TOKEN_PATH, AuthRequestBodyFactory.create(), AuthResponse.class);

        assertThat(response.getBody().getToken()).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void shouldNotHandleInvalidEmail() {
        ResponseEntity<AuthResponse> response = template.postForEntity(
                AuthController.GET_TOKEN_PATH,
                AuthRequestBodyFactory.create("invalidEmail", AuthRequestBodyFactory.PASSWORD),
                AuthResponse.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldNotHandleInvalidPassword() {
        ResponseEntity<AuthResponse> response = template.postForEntity(
                AuthController.GET_TOKEN_PATH,
                AuthRequestBodyFactory.create(AuthRequestBodyFactory.EMAIL, "tooLongPassword123213123123abacabca"),
                AuthResponse.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
