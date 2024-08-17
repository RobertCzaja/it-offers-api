package pl.api.itoffers.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.api.itoffers.security.application.service.AuthorizationService;
import pl.api.itoffers.security.application.service.JwtService;
import pl.api.itoffers.security.infrastructure.UserInMemoryRepository;


@Component
public class ApiAuthorizationHelper {

    private AuthorizationService authorizationService;

    public ApiAuthorizationHelper(JwtService jwtService, BCryptPasswordEncoder passwordEncoder) {
        this.authorizationService = new AuthorizationService(
                new UserInMemoryRepository(),
                jwtService,
                passwordEncoder
        );
    }

    public HttpHeaders getHeaders(AuthorizationCredentials credentials) {
        String token = authorizationService.getToken(credentials.getEmail(), credentials.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);

        return headers;
    }
}
