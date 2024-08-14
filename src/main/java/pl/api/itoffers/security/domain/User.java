package pl.api.itoffers.security.domain;

import pl.api.itoffers.security.domain.model.UserRole;

/**
 * TODO name to change, is it a model only for JWT?
 */
@Deprecated
public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // TODO get Roles from db
    public UserRole[] getRoles() {
        return new UserRole[]{UserRole.ROLE_USER};
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
