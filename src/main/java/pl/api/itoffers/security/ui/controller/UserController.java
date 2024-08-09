package pl.api.itoffers.security.ui.controller;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.security.domain.model.UserEntity;
import pl.api.itoffers.security.infrastructure.UserPostgresRepository;
import pl.api.itoffers.security.ui.request.CreateUserRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    public final static String PATH = "/user";

    @Autowired
    private UserPostgresRepository repository;

    @PostMapping(UserController.PATH)
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody CreateUserRequest request)
    {
        /*
         * TODO move that to Service
         *  create response object
         *  make hashed password
         *  create Functional Test
         */
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDate(LocalDateTime.now());

        repository.save(user);

        Map<String, String> result = new HashMap<>();
        result.put("message", "User created");
        result.put("id", user.getId().toString());

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
