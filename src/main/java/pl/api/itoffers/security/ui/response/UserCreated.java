package pl.api.itoffers.security.ui.response;

import lombok.Data;

@Data
public class UserCreated {
  private Long userId;
  private String message = "User created";
}
