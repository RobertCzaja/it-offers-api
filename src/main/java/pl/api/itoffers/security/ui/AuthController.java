package pl.api.itoffers.security.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.security.application.JwtUtil;
import pl.api.itoffers.security.application.User;
import pl.api.itoffers.security.infrastructure.UserInMemoryRepository;

@RestController
public class AuthController {

    public final static String GET_TOKEN_PATH = "/auth";

    private final JwtUtil jwtUtil;
    private final UserInMemoryRepository userRepository;

    public AuthController(JwtUtil jwtUtil, UserInMemoryRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @GetMapping(AuthController.GET_TOKEN_PATH)
    public ResponseEntity<AuthResponse> auth(@RequestParam(required = false) String email)
    {
        if (null == email) {
            throw new RuntimeException("Email must be provided");
        }

        User user = userRepository.findUserByEmail(email);

        var authResponse = new AuthResponse(jwtUtil.createToken(user));
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String testingEndpoint()
    {
        return "Secret information";
    }
}
