package pl.api.itoffers.provider.nofluffjobs.exception;

public class NoFluffJobsException extends RuntimeException {
  public NoFluffJobsException(String message, Exception e) {
    super(message, e);
  }

  public NoFluffJobsException(String message) {
    super(message);
  }

  public static NoFluffJobsException payloadIsEmpty() {
    return new NoFluffJobsException("Empty body payload");
  }
}
