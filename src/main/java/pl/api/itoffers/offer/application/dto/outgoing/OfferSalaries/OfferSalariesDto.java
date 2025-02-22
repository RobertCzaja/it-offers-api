package pl.api.itoffers.offer.application.dto.outgoing.OfferSalaries;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;

@Value
@RequiredArgsConstructor
public class OfferSalariesDto {
  Integer amountFrom;
  Integer amountTo;
  String currency;
  String technology;
  String title;
  String link;

  public static List<OfferSalariesDto> createFrom(
      Offer offer, String currency, String employmentType) {
    List<OfferSalariesDto> list = new ArrayList<OfferSalariesDto>();
    for (Salary salary : offer.getSalaries()) {
      if (salary.getAmount().getCurrency().equalsIgnoreCase(currency)
          && salary.getEmploymentType().equalsIgnoreCase(employmentType)) {
        list.add(
            new OfferSalariesDto(
                salary.getAmount().getFrom(),
                salary.getAmount().getTo(),
                salary.getAmount().getCurrency(),
                offer.getTechnology(),
                offer.getTitle(),
                offer.getSlug()));
      }
    }
    return list;
  }
}
