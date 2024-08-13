package pl.api.itoffers.security.ui.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import pl.api.itoffers.security.application.service.JwtService;
import pl.api.itoffers.security.domain.User;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.domain.exception.UserNotFound;
import pl.api.itoffers.security.ui.request.AuthorizationRequest;
import pl.api.itoffers.security.ui.response.AuthResponse;

@RestController
public class AuthController {

    public final static String GET_TOKEN_PATH = "/auth";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /* TODO here must be used real User repository but in Tests it should be InMemory */
    /* TODO to refactor: move that code to Application Service */
    @PostMapping(AuthController.GET_TOKEN_PATH)
    public ResponseEntity<AuthResponse> auth(@Valid @RequestBody AuthorizationRequest request)
    {
        try {
            User user = userRepository.findUserByEmail(request.getEmail());

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            AuthResponse response = new AuthResponse();
            response.setToken(jwtService.createToken(user));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
