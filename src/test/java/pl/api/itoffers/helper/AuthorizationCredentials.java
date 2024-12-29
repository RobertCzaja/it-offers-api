package pl.api.itoffers.helper;

import pl.api.itoffers.security.infrastructure.UserInMemoryRepository;

public enum AuthorizationCredentials {
  USER(UserInMemoryRepository.EMAIL_USER, UserInMemoryRepository.PASSWORD_USER),
  ADMIN(UserInMemoryRepository.EMAIL_ADMIN, UserInMemoryRepository.PASSWORD_ADMIN);

  private String email;
  private String password;

  AuthorizationCredentials(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
