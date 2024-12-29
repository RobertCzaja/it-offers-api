package pl.api.itoffers.unit.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.security.domain.User;
import pl.api.itoffers.security.domain.model.UserEntity;
import pl.api.itoffers.security.domain.model.UserRole;

public class UserEntityTest {

  @Test
  void shouldCreateModel() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(Long.valueOf(1));
    userEntity.setDate(LocalDateTime.now());
    userEntity.setEmail("a@a.com");
    userEntity.setPassword("hashedPassword");
    userEntity.setRoles(
        new String[] {UserRole.ROLE_USER.toString(), UserRole.ROLE_ADMIN.toString()});

    User user = userEntity.castToUser();

    assertThat(user.getRoles()).isEqualTo(new UserRole[] {UserRole.ROLE_USER, UserRole.ROLE_ADMIN});
  }
}
