package pl.api.itoffers.offer.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.repository.OfferReadRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportSalariesService {

    private final OfferReadRepository offerReadRepository;

    /**
     * todo WIP
     * todo "to"/"currency" parameters supposed to be passed as method arguments
     */
    public List<OfferDto> getMostPaidOffers(String currency, String employmentType, Integer amountTo) {
        return offerReadRepository.getBySalary(amountTo, currency, employmentType);
    }
}
