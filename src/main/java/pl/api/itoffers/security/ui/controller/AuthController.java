package pl.api.itoffers.security.ui.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.security.application.service.JwtService;
import pl.api.itoffers.security.domain.User;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.ui.request.AuthorizationRequest;
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

    @PostMapping(AuthController.GET_TOKEN_PATH)
    public AuthResponse auth(@Valid @RequestBody AuthorizationRequest request)
    {
        User user = userRepository.findUserByEmail(request.getEmail());

        AuthResponse response = new AuthResponse();
        response.setToken(jwtService.createToken(user));

        return response;
    }
}
