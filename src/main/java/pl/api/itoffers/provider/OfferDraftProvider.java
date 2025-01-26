package pl.api.itoffers.provider;

import java.util.List;
import java.util.UUID;
import pl.api.itoffers.offer.domain.OfferDraft;

public interface OfferDraftProvider {
  List<OfferDraft> getList(UUID scrapingId, String technology);
}
