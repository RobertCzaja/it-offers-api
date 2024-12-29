package pl.api.itoffers.security.ui.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorizationRequest {
  @NotBlank
  @Email(message = "Invalid email")
  private String email;

  @NotBlank
  @Size(max = 15, message = "Password is too long")
  private String password;
}
