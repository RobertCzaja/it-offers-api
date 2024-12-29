package pl.api.itoffers.security.domain;

import lombok.Data;
import pl.api.itoffers.security.domain.model.UserRole;

/** TODO name to change, is it a model only for JWT? */
@Data
@Deprecated
public class User {
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private UserRole[] roles;

  public User(UserRole[] roles, String email, String password, String firstName, String lastName) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.roles = roles;
  }
}
