package pl.api.itoffers.integration.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.ApiAuthorizationHelper;
import pl.api.itoffers.helper.AuthorizationCredentials;
import pl.api.itoffers.helper.UserFactory;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.ui.controller.UserController;
import pl.api.itoffers.security.ui.request.CreateUserRequest;
import pl.api.itoffers.security.ui.response.UserCreated;

public class UserControllerITest extends AbstractITest {

  @Autowired private TestRestTemplate template;
  @Autowired private ApiAuthorizationHelper apiAuthorizationHelper;
  @Autowired private UserRepository userRepository;

  @BeforeEach
  @Override
  public void setUp() {
    userRepository.deleteAll();
    super.setUp();
  }

  @Test
  public void shouldCreateUser() {

    CreateUserRequest requestBody = UserFactory.createUserRequest();
    HttpEntity<CreateUserRequest> request =
        new HttpEntity<>(
            requestBody, apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN));

    ResponseEntity<UserCreated> response =
        template.postForEntity(UserController.PATH, request, UserCreated.class);

    assertThat(response.getBody().getMessage()).contains("User created");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(userRepository.findUserByEmail(requestBody.getEmail())).isNotNull();

    ResponseEntity<String> responseForDuplicatedRequest =
        template.postForEntity(UserController.PATH, request, String.class);
    assertThat(responseForDuplicatedRequest.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
  }

  @Test
  public void shouldNotCreateUserDueToInvalidEmail() {

    CreateUserRequest requestBody = UserFactory.createUserRequest("someInvalidEmail");
    HttpEntity<CreateUserRequest> request =
        new HttpEntity<>(
            requestBody, apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN));

    ResponseEntity<UserCreated> response =
        template.postForEntity(UserController.PATH, request, UserCreated.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void shouldNotAllowToCreateUserLoggedUserWithRoleUser() {

    CreateUserRequest requestBody = UserFactory.createUserRequest();
    HttpEntity<CreateUserRequest> request =
        new HttpEntity<>(
            requestBody, apiAuthorizationHelper.getHeaders(AuthorizationCredentials.USER));

    ResponseEntity<UserCreated> response =
        template.postForEntity(UserController.PATH, request, UserCreated.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    assertThat(response.getBody().getMessage()).isEqualTo("Access denied");
  }
}
