package pl.api.itoffers.security.ui.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 5, max = 15)
    private String password;
    @Valid
    private UserNameRequest name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserNameRequest getName() {
        return name;
    }

    public void setName(UserNameRequest name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "email=" + email +", password=" + password + ", name=" + name.getFirst() + " " + name.getLast();
    }
}
