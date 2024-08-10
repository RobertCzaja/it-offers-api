package pl.api.itoffers.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.security.ui.controller.UserController;
import pl.api.itoffers.security.ui.request.CreateUserRequest;
import pl.api.itoffers.security.ui.request.UserNameRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.shell.interactive.enabled=false"}
)
public class UserControllerITest {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ApiAuthorizationHelper apiAuthorizationHelper;

    @Test
    public void shouldCreateUser() throws Exception {

        HttpEntity<CreateUserRequest> request = new HttpEntity<>(createRequest(), apiAuthorizationHelper.getHeaders());

        ResponseEntity<String> response = template.postForEntity(UserController.PATH, request, String.class);

        assertThat(response.getBody()).contains("User created");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // TODO gets user from DB
    }

    private static CreateUserRequest createRequest()
    {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        UserNameRequest userName = new UserNameRequest();
        createUserRequest.setEmail("b@b.com");
        createUserRequest.setPassword("admin1234");
        userName.setFirst("John");
        userName.setLast("Doe");
        createUserRequest.setName(userName);
        return createUserRequest;
    }
}
