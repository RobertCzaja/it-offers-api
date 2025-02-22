package pl.api.itoffers.security.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.api.itoffers.security.application.service.UserService;
import pl.api.itoffers.security.infrastructure.UserPostgresRepository;

@Configuration
public class UserServiceConfig {

  @Bean
  public UserService userService(
      UserPostgresRepository userPostgresRepository, BCryptPasswordEncoder passwordEncoder) {
    return new UserService(userPostgresRepository, passwordEncoder);
  }
}
