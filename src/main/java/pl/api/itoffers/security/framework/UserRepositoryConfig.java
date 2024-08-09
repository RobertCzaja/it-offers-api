package pl.api.itoffers.security.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.api.itoffers.security.infrastructure.UserInMemoryRepository;
import pl.api.itoffers.security.infrastructure.UserPostgresRepository;

@Configuration
public class UserRepositoryConfig {

    @Autowired
    private UserInMemoryRepository userInMemoryRepository;
    @Autowired
    private UserPostgresRepository userPostgresRepository;

    @Bean
    @Primary
    public UserInMemoryRepository inMemory()
    {
        return userInMemoryRepository;
    }

    @Bean
    public UserPostgresRepository postgreSQL()
    {
        return userPostgresRepository;
    }
}
