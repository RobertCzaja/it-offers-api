package pl.api.itoffers.security.ui.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.security.application.service.JwtService;
import pl.api.itoffers.security.domain.User;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.ui.response.AuthResponse;

@RestController
public class AuthController {

    public final static String GET_TOKEN_PATH = "/auth";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @GetMapping(AuthController.GET_TOKEN_PATH)
    public ResponseEntity<AuthResponse> auth(@RequestParam(required = false) String email)
    {
        if (null == email) {
            throw new RuntimeException("Email must be provided");
        }



        User user = userRepository.findUserByEmail(email);

        var authResponse = new AuthResponse(jwtService.createToken(user));
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String testingEndpoint()
    {
        return "Secret information";
    }
}