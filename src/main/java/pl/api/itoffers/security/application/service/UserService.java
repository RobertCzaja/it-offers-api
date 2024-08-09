package pl.api.itoffers.security.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.domain.model.UserEntity;
import pl.api.itoffers.security.ui.request.CreateUserRequest;

import java.time.LocalDateTime;

@Component
public class UserService {

    @Autowired
    @Qualifier("postgreSQL")
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /* TODO create Functional Test */
    public Long create(CreateUserRequest request)
    {
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDate(LocalDateTime.now());

        repository.save(user);

        return user.getId();
    }
}
