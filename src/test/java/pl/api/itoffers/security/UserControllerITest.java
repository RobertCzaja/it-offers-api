package pl.api.itoffers.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.ui.controller.UserController;
import pl.api.itoffers.security.ui.request.CreateUserRequest;
import pl.api.itoffers.security.ui.request.UserNameRequest;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.shell.interactive.enabled=false"}
)
public class UserControllerITest {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ApiAuthorizationHelper apiAuthorizationHelper;
    @Autowired
    @Qualifier("postgreSQL")
    private UserRepository userRepository;

    @Test
    public void shouldCreateUser() throws Exception {

        CreateUserRequest requestBody = createRequest();
        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestBody, apiAuthorizationHelper.getHeaders());

        ResponseEntity<String> response = template.postForEntity(UserController.PATH, request, String.class);

        assertThat(response.getBody()).contains("User created");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userRepository.findUserByEmail(requestBody.getEmail())).isNotNull();
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
