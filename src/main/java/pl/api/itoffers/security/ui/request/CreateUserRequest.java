package pl.api.itoffers.security.ui.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank
    @Email(message="Invalid email")
    private String email;
    @NotBlank
    @Size(min = 5, max = 15)
    private String password;
    @Valid
    private UserNameRequest name;

    @Deprecated
    public String getEmail() {
        return email;
    }

    @Deprecated
    public void setEmail(String email) {
        this.email = email;
    }

    @Deprecated
    public String getPassword() {
        return password;
    }

    @Deprecated
    public void setPassword(String password) {
        this.password = password;
    }

    @Deprecated
    public UserNameRequest getName() {
        return name;
    }

    @Deprecated
    public void setName(UserNameRequest name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "email=" + email +", password=" + password + ", name=" + name.getFirst() + " " + name.getLast();
    }
}
