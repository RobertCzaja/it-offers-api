package pl.api.itoffers.provider;

import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.provider.justjoinit.infrastructure.JustJoinItParameters;
import pl.api.itoffers.provider.nofluffjobs.fetcher.NoFluffJobsParameters;

@Service
@RequiredArgsConstructor
public class ProviderFacade {
  private final JustJoinItParameters justJoinItParameters;
  private final NoFluffJobsParameters noFluffJobsParameters;

  public URL getOfferUrl(Offer offer) {
    if (offer.getOrigin().getProvider().equals(Origin.Provider.JUST_JOIN_IT)) {
      return justJoinItParameters.getOfferUrl(offer.getSlug());
    }
    if (offer.getOrigin().getProvider().equals(Origin.Provider.NO_FLUFF_JOBS)) {
      throw ProviderException.notImplementedYet();
    }
    throw ProviderException.unknownProvider(offer.getOrigin().getProvider().name());
  }
}
