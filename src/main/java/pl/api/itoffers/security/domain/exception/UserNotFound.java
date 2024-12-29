package pl.api.itoffers.security.domain.exception;

public class UserNotFound extends RuntimeException {
  public UserNotFound(String message) {
    super(message);
  }

  public static UserNotFound with(String email) {
    return new UserNotFound("User with email " + email + " not exists");
  }
}
