package pl.api.itoffers.offer.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.dto.incoming.DatesRangeFilter;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDtoDeprecated;
import pl.api.itoffers.offer.application.repository.OfferReadRepository;

@Service
@RequiredArgsConstructor
public class ReportSalariesService {

  private final OfferReadRepository offerReadRepository;

  public List<OfferDtoDeprecated> getMostPaidOffers(
      String currency,
      String employmentType,
      Integer amountTo,
      List<String> technologies,
      DatesRangeFilter datesRange) {
    return offerReadRepository.getBySalary(
        amountTo, currency, employmentType, technologies, datesRange.getFrom(), datesRange.getTo());
  }
}
