package pl.api.itoffers.security.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    public final static String GET_TOKEN_PATH = "/auth";

    @GetMapping(AuthController.GET_TOKEN_PATH)
    public ResponseEntity<AuthResponse> auth()
    {
        var authResponse = new AuthResponse("token");
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String testingEndpoint()
    {
        return "Secret information";
    }
}
