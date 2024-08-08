package pl.api.itoffers.security.ui.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.security.ui.request.CreateUserRequest;

@RestController
public class UserController {

    public final static String PATH = "/user";

    public UserController() {
    }

    @PostMapping(UserController.PATH)
    public ResponseEntity<String> create(@Valid @RequestBody CreateUserRequest request)
    {
        return new ResponseEntity<String>(request.toString(), HttpStatus.CREATED);
    }
}
