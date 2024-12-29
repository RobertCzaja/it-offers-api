package pl.api.itoffers.provider.justjoinit.exception;

public class JustJoinItException extends RuntimeException {
  public JustJoinItException(String message) {
    super(message);
  }

  public JustJoinItException(String message, Exception e) {
    super(message, e);
  }
}
