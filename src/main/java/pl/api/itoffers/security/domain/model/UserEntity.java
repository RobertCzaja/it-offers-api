package pl.api.itoffers.security.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.Data;
import pl.api.itoffers.security.domain.User;

@Data
@Entity
@Table(name = "\"user\"") // TODO to find out why Hibernate can't generate "" by itself
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @Column(unique = true)
  private String email;

  @NotNull
  @Column(nullable = false)
  private String password;

  @NotNull
  @Column(nullable = false)
  private LocalDateTime date;

  @Column(nullable = false)
  private String[] roles;

  @Deprecated
  public User castToUser() {
    ArrayList<UserRole> enumRoles = new ArrayList<>();
    for (String role : roles) {
      enumRoles.add(UserRole.valueOf(role));
    }

    return new User(enumRoles.toArray(new UserRole[0]), email, password, "", "");
  }
}
