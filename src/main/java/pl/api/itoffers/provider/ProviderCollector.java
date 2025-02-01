package pl.api.itoffers.provider;

import java.util.UUID;

public interface ProviderCollector {
  void collectOffers(UUID scrapingId, String technology);

  String providerName();
}
