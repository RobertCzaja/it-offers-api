package pl.api.itoffers.offer.application.repository;

import java.time.LocalDateTime;
import java.util.List;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDtoDeprecated;

public interface OfferReadRepository {
  /**
   * @deprecated todo to remove in #59
   */
  List<OfferDtoDeprecated> getBySalary(
      int amountTo,
      String currency,
      String employmentType,
      List<String> technologies,
      LocalDateTime from,
      LocalDateTime to);

  List<OfferDto> getList(List<String> technologies, LocalDateTime from, LocalDateTime to);
}
