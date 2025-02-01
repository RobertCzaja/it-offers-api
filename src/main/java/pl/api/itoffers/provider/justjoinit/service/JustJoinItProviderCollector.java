package pl.api.itoffers.provider.justjoinit.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.Origin;
import pl.api.itoffers.provider.ProviderCollector;

@Service
@RequiredArgsConstructor
@Slf4j
public class JustJoinItProviderCollector implements ProviderCollector {
  private final JustJoinItProvider justJoinItProvider;

  @Override
  public void collectOffers(UUID scrapingId, String technology) {
    justJoinItProvider.fetch(technology, scrapingId);
  }

  @Override
  public String providerName() {
    return Origin.Provider.JUST_JOIN_IT.name();
  }
}
