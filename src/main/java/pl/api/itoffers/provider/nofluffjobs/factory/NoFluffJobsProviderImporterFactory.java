package pl.api.itoffers.provider.nofluffjobs.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.offer.application.service.TechnologiesProvider;
import pl.api.itoffers.provider.ProviderImporterTemplate;
import pl.api.itoffers.provider.nofluffjobs.service.NoFluffJobsOfferDraftProvider;
import pl.api.itoffers.provider.nofluffjobs.service.NoFluffJobsProviderCollector;

@Service
@RequiredArgsConstructor
public class NoFluffJobsProviderImporterFactory {

  private final NoFluffJobsProviderCollector collector;
  private final NoFluffJobsOfferDraftProvider offerDraftProvider;
  private final OfferSaver offerSaver;
  private final TechnologiesProvider technologiesProvider;

  public ProviderImporterTemplate create() {
    return new ProviderImporterTemplate(
        collector, offerDraftProvider, offerSaver, technologiesProvider);
  }
}
