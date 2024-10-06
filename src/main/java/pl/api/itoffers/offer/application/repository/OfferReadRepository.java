package pl.api.itoffers.offer.application.repository;

import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;

import java.util.List;

public interface OfferReadRepository {
    List<OfferDto> getBySalary(int amountTo, String currency, String employmentType, List<String> technologies);
}
