package pl.api.itoffers.helper;

import org.springframework.http.HttpEntity;
import pl.api.itoffers.security.infrastructure.UserInMemoryRepository;
import pl.api.itoffers.security.ui.request.AuthorizationRequest;

public class AuthRequestBodyFactory {
    public static final String EMAIL = UserInMemoryRepository.EMAIL_USER;
    public static final String PASSWORD = UserInMemoryRepository.PASSWORD_USER;

    public static HttpEntity<AuthorizationRequest> create(AuthorizationCredentials credentials)
    {
        return create(credentials.getEmail(), credentials.getPassword());
    }

    public static HttpEntity<AuthorizationRequest> create(String email, String password)
    {
        AuthorizationRequest requestBody = new AuthorizationRequest();
        requestBody.setEmail(email);
        requestBody.setPassword(password);

        return new HttpEntity<>(requestBody);
    }
}
