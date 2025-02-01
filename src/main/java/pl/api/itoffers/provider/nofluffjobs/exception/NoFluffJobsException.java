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

  public static NoFluffJobsException onIncompleteStructure(String json, String missingField) {
    return new NoFluffJobsException(String.format("Missing %s in: %s", missingField, json));
  }

  public static NoFluffJobsException onFetchingHtmlPage(Throwable t) {
    return new NoFluffJobsException(
        String.format(
            "On fetching HTML page - %s with a message: %s",
            t.getClass().getName(), t.getMessage()),
        t);
  }

  public static NoFluffJobsException onMappingToDomainModel(String field, String json) {
    return new NoFluffJobsException(String.format("Cannot map filed %s from: %s", field, json));
  }

  public static NoFluffJobsException becauseFetchingResultIsIncompatible(String message) {
    return new NoFluffJobsException(String.format("Fetching result is incompatible - %s", message));
  }
}
