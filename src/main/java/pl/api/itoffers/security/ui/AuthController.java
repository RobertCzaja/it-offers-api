package pl.api.itoffers.security.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/auth")
    public ResponseEntity<AuthResponse> auth()
    {
        var authResponse = new AuthResponse("token1234");
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}
