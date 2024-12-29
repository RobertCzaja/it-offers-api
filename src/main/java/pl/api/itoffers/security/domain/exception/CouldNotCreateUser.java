package pl.api.itoffers.security.domain.exception;

public final class CouldNotCreateUser extends RuntimeException {
  private CouldNotCreateUser(String message, Throwable t) {
    super(message, t);
  }

  public static CouldNotCreateUser becauseEmailIsAlreadyRegistered(String email, Throwable t) {
    return new CouldNotCreateUser(String.format("Email %s is already registered", email), t);
  }
}
