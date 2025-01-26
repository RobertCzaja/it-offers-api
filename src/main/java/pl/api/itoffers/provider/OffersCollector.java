package pl.api.itoffers.provider;

import org.jetbrains.annotations.NotNull;

public interface OffersCollector {

  void collectFromProvider(@NotNull String technology);
}
