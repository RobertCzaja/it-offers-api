package pl.api.itoffers.helper;

import pl.api.itoffers.security.ui.request.CreateUserRequest;
import pl.api.itoffers.security.ui.request.UserNameRequest;

public class UserFactory {
  public static CreateUserRequest createUserRequest(String email) {
    CreateUserRequest createUserRequest = new CreateUserRequest();
    UserNameRequest userName = new UserNameRequest();
    createUserRequest.setEmail(email == null ? "b@b.com" : email);
    createUserRequest.setPassword("admin1234");
    userName.setFirst("John");
    userName.setLast("Doe");
    createUserRequest.setName(userName);
    return createUserRequest;
  }

  public static CreateUserRequest createUserRequest() {
    return createUserRequest("b@b.com");
  }
}
