package pl.api.itoffers.helper;

import org.springframework.http.HttpEntity;
import pl.api.itoffers.security.ui.request.AuthorizationRequest;

public class AuthRequestBodyFactory {
    public static final String EMAIL = "a@a.pl";
    public static final String PASSWORD = "admin1234";

    public static HttpEntity<AuthorizationRequest> create()
    {
        return create(EMAIL, PASSWORD);
    }

    public static HttpEntity<AuthorizationRequest> create(String email, String password)
    {
        AuthorizationRequest requestBody = new AuthorizationRequest();
        requestBody.setEmail(email);
        requestBody.setPassword(password);

        return new HttpEntity<>(requestBody);
    }


}
