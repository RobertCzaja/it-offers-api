package pl.api.itoffers.security.application.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.api.itoffers.security.application.repository.UserRepository;
import pl.api.itoffers.security.domain.exception.CouldNotCreateUser;
import pl.api.itoffers.security.domain.model.UserEntity;
import pl.api.itoffers.security.domain.model.UserRole;
import pl.api.itoffers.security.ui.request.CreateUserRequest;

@Component
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final BCryptPasswordEncoder passwordEncoder;

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
