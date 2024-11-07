package pl.api.itoffers.offer.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.dto.incoming.DatesRangeFilter;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.repository.OfferReadRepository;

import java.util.List;

/** @deprecated todo to remove in #59 */
@Service
@RequiredArgsConstructor
public class ReportSalariesService {

    private final OfferReadRepository offerReadRepository;

    public List<OfferDto> getMostPaidOffers(
        String currency,
        String employmentType,
        Integer amountTo,
        List<String> technologies,
        DatesRangeFilter datesRange
    ) {
        return offerReadRepository.getBySalary(
            amountTo,
            currency,
            employmentType,
            technologies,
            datesRange.getFrom(),
            datesRange.getTo()
        );
    }
}
