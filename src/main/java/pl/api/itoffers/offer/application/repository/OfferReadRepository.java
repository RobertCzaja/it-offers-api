package pl.api.itoffers.offer.application.repository;

import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto2;

import java.time.LocalDateTime;
import java.util.List;

public interface OfferReadRepository {
    /** @deprecated todo to remove in #59 */
    List<OfferDto> getBySalary(
        int amountTo,
        String currency,
        String employmentType,
        List<String> technologies,
        LocalDateTime from,
        LocalDateTime to
    );

    List<OfferDto2> getList(
        List<String> technologies,
        LocalDateTime from,
        LocalDateTime to
    );
}
