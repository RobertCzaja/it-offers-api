package pl.api.itoffers.provider.nofluffjobs.exception;

public class NoFluffJobsException extends RuntimeException {
  public NoFluffJobsException(String message, Throwable t) {
    super(message, t);
  }

  public NoFluffJobsException(String message) {
    super(message);
  }

  public static NoFluffJobsException payloadIsEmpty() {
    return new NoFluffJobsException("Empty body payload");
  }

  public static NoFluffJobsException onExtractingOffers(String json, Throwable t) {
    return new NoFluffJobsException(String.format("Error on extracting offers from: %s", json), t);
  }

  public static NoFluffJobsException onFetchingHtmlPage(Throwable t) {
    return new NoFluffJobsException("On fetching HTML page", t);
  }
}
