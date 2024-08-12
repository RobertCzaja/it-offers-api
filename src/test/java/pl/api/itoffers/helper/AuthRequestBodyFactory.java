package pl.api.itoffers.helper;

import org.springframework.http.HttpEntity;
import pl.api.itoffers.security.ui.request.AuthorizationRequest;

public class AuthRequestBodyFactory {
    public static HttpEntity<AuthorizationRequest> create()
    {
        AuthorizationRequest requestBody = new AuthorizationRequest();
        requestBody.setEmail("a@a.pl");
        requestBody.setPassword("admin1234");

        return new HttpEntity<>(requestBody);
    }
}
