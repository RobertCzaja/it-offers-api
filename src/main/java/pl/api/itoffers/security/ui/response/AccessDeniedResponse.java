package pl.api.itoffers.security.ui.response;

import lombok.Data;

@Data
public class AccessDeniedResponse {
  private String message = "Access denied";
}
