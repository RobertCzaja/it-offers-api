package pl.api.itoffers.provider.justjoinit.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.domain.OfferDraft;
import pl.api.itoffers.provider.OfferDraftProvider;
import pl.api.itoffers.provider.justjoinit.factory.OfferFactory;
import pl.api.itoffers.provider.justjoinit.factory.SalariesFactory;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;

@Service
@RequiredArgsConstructor
public class JustJoinItOfferDraftProvider implements OfferDraftProvider {

  private final JustJoinItRepository jjitRawOffersRepository;
  private final SalariesFactory salariesFactory;

  @Override
  public List<OfferDraft> getList(UUID scrapingId, String technology) {
    return jjitRawOffersRepository.findByScrapingIdAndTechnology(scrapingId, technology).stream()
        .map(
            rawOffer ->
                new OfferDraft(
                    OfferFactory.createOrigin(rawOffer),
                    OfferFactory.createOfferMetadata(rawOffer),
                    OfferFactory.createCategories(rawOffer),
                    salariesFactory.create(rawOffer),
                    OfferFactory.createCompany(rawOffer)))
        .toList();
  }
}
