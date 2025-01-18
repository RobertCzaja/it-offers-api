package pl.api.itoffers.provider.nofluffjobs.fetcher;

import java.util.ArrayList;
import java.util.List;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawOffer;

public final class RawDataMatcher {

  private RawDataMatcher() {}

  public static List<NoFluffJobsRawOffer> match(
      List<NoFluffJobsRawListOffer> listOffers, List<NoFluffJobsRawDetailsOffer> detailsOffers) {

    if (listOffers.isEmpty() || detailsOffers.isEmpty()) {
      throw NoFluffJobsException.becauseFetchingResultIsIncompatible("empty collections");
    }

    if (listOffers.size() != detailsOffers.size()) {
      throw NoFluffJobsException.becauseFetchingResultIsIncompatible("initial collections");
    }

    var matched = new ArrayList<NoFluffJobsRawOffer>();

    for (var listOffer : listOffers) {
      for (var detailsOffer : detailsOffers) {
        if (listOffer.getOfferId().equals(detailsOffer.getOfferId())) {
          matched.add(new NoFluffJobsRawOffer(listOffer, detailsOffer));
          break;
        }
      }
    }

    if (matched.size() != listOffers.size()) {
      throw NoFluffJobsException.becauseFetchingResultIsIncompatible("could not match each offer");
    }

    return matched;
  }
}
