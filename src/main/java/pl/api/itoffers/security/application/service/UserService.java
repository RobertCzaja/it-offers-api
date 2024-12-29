package pl.api.itoffers.security.application.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.domain.exception.CouldNotCreateUser;
import pl.api.itoffers.security.domain.model.UserEntity;
import pl.api.itoffers.security.domain.model.UserRole;
import pl.api.itoffers.security.ui.request.CreateUserRequest;

@Component
public class UserService {

  @Autowired
  @Qualifier("postgreSQL")
  private UserRepository repository;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  public Long create(CreateUserRequest request) throws CouldNotCreateUser {
    return create(UserRole.getStandardRoles(), request.getEmail(), request.getPassword());
  }

  public Long create(String[] roles, String email, String password) throws CouldNotCreateUser {
    UserEntity user = new UserEntity();
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setDate(LocalDateTime.now());
    user.setRoles(roles);

    repository.save(user);

    return user.getId();
  }
}
