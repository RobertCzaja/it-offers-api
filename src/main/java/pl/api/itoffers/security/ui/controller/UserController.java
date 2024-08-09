package pl.api.itoffers.security.ui.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.security.application.service.UserService;
import pl.api.itoffers.security.ui.request.CreateUserRequest;
import pl.api.itoffers.security.ui.response.UserCreated;

@RestController
public class UserController {

    public final static String PATH = "/user";

    @Autowired
    private UserService userService;

    @PostMapping(UserController.PATH)
    public ResponseEntity<UserCreated> create(@Valid @RequestBody CreateUserRequest request)
    {
        return new ResponseEntity<>(
                new UserCreated(userService.create(request)),
                HttpStatus.CREATED
        );
    }
}
