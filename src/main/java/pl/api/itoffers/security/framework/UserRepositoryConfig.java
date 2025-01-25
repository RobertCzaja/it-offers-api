package pl.api.itoffers.security.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.api.itoffers.security.infrastructure.UserInMemoryRepository;
import pl.api.itoffers.security.infrastructure.UserPostgresRepository;

@Configuration
public class UserRepositoryConfig {

  @Bean
  public UserInMemoryRepository inMemory() {
    return new UserInMemoryRepository();
  }

  @Bean
  @Primary
  public UserPostgresRepository postgreSQL(UserPostgresRepository userPostgresRepository) {
    return userPostgresRepository;
  }
}
