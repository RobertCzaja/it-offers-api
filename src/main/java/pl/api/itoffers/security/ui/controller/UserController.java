package pl.api.itoffers.security.ui.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.security.application.service.UserService;
import pl.api.itoffers.security.domain.exception.CouldNotCreateUser;
import pl.api.itoffers.security.ui.request.CreateUserRequest;
import pl.api.itoffers.security.ui.response.UserCreated;
import pl.api.itoffers.shared.http.exception.ErrorResponse;

@RestController
public class UserController {

    public final static String PATH = "/user";

    @Autowired
    private UserService userService;

    //@PreAuthorize("hasRole('USER')")
    @PostMapping(UserController.PATH)
    public ResponseEntity create(@Valid @RequestBody CreateUserRequest request) throws CouldNotCreateUser {
        try {
            UserCreated response = new UserCreated();
            response.setUserId(userService.create(request));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (CouldNotCreateUser e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
