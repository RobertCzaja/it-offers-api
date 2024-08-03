package pl.api.itoffers.security.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.security.application.JwtUtil;
import pl.api.itoffers.security.application.User;

@RestController
public class AuthController {

    public final static String GET_TOKEN_PATH = "/auth";
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(AuthController.GET_TOKEN_PATH)
    public ResponseEntity<AuthResponse> auth()
    {
        User user = new User("example@email.com", "admin", "John", "Doe");

        var authResponse = new AuthResponse(jwtUtil.createToken(user));
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String testingEndpoint()
    {
        return "Secret information";
    }
}
