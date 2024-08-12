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
}
