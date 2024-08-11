package pl.api.itoffers.helper;

import pl.api.itoffers.security.ui.request.CreateUserRequest;
import pl.api.itoffers.security.ui.request.UserNameRequest;

public class UserFactory {
    public static CreateUserRequest createUserRequest()
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
