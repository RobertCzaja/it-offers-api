package pl.api.itoffers.provider;

public final class ProviderException extends RuntimeException {
  private ProviderException(String message) {
    super(message);
  }

  public static ProviderException unknownProvider(String name) {
    return new ProviderException(String.format("Unknown provider with name %s", name));
  }

  public static ProviderException notImplementedYet() {
    return new ProviderException("Not implemented yet");
  }
}
