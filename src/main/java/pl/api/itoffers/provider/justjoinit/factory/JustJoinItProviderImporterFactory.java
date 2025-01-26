package pl.api.itoffers.provider.justjoinit.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.offer.application.service.TechnologiesProvider;
import pl.api.itoffers.provider.ProviderImporterTemplate;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItOfferDraftProvider;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItProviderCollector;

@Service
@RequiredArgsConstructor
public class JustJoinItProviderImporterFactory {

  private final JustJoinItOfferDraftProvider offerDraftProvider;
  private final OfferSaver offerSaver;
  private final TechnologiesProvider technologiesProvider;
  private final JustJoinItProviderCollector collector;

  public ProviderImporterTemplate create() {
    return new ProviderImporterTemplate(
        collector, offerDraftProvider, offerSaver, technologiesProvider);
  }
}
