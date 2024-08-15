package pl.api.itoffers.security.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.api.itoffers.security.domain.model.UserRole;

import java.util.Arrays;

/**
 * TODO name to change, is it a model only for JWT?
 */
@Data
@Deprecated
public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole[] roles;

    public User(String email, String password, String firstName, String lastName, UserRole[] roles) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }
}
