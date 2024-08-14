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
import pl.api.itoffers.helper.ApiAuthorizationHelper;
import pl.api.itoffers.helper.AuthorizationCredentials;
import pl.api.itoffers.helper.UserFactory;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.ui.controller.UserController;
import pl.api.itoffers.security.ui.request.CreateUserRequest;
import pl.api.itoffers.security.ui.response.UserCreated;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

        CreateUserRequest requestBody = UserFactory.createUserRequest();
        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestBody, apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN));

        ResponseEntity<UserCreated> response = template.postForEntity(UserController.PATH, request, UserCreated.class);

        assertThat(response.getBody().getMessage()).contains("User created");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userRepository.findUserByEmail(requestBody.getEmail())).isNotNull();

        ResponseEntity<String> responseForDuplicatedRequest = template.postForEntity(UserController.PATH, request, String.class);
        assertThat(responseForDuplicatedRequest.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void shouldNotCreateUserDueToInvalidEmail() throws Exception {

        CreateUserRequest requestBody = UserFactory.createUserRequest("someInvalidEmail");
        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestBody, apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN));

        ResponseEntity<UserCreated> response = template.postForEntity(UserController.PATH, request, UserCreated.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotAllowToCreateUserLoggedUserWithRoleUser() throws Exception {

        CreateUserRequest requestBody = UserFactory.createUserRequest();
        HttpEntity<CreateUserRequest> request = new HttpEntity<>(requestBody, apiAuthorizationHelper.getHeaders(AuthorizationCredentials.USER));

        ResponseEntity<UserCreated> response = template.postForEntity(UserController.PATH, request, UserCreated.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
