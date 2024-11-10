package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;

import java.util.ArrayList;
import java.util.List;

/** @deprecated todo remove in #59 */
@Value
@RequiredArgsConstructor
public class OfferDtoDeprecated {
    Integer amountFrom;
    Integer amountTo;
    String currency;
    String technology;
    String title;
    String link;

    public static List<OfferDtoDeprecated> createFrom(Offer offer, String currency, String employmentType) {
        List<OfferDtoDeprecated> list = new ArrayList<OfferDtoDeprecated>();
        for (Salary salary : offer.getSalaries()) {
            if (salary.getAmount().getCurrency().equalsIgnoreCase(currency) && salary.getEmploymentType().equalsIgnoreCase(employmentType)) {
                list.add(new OfferDtoDeprecated(
                    salary.getAmount().getFrom(),
                    salary.getAmount().getTo(),
                    salary.getAmount().getCurrency(),
                    offer.getTechnology(),
                    offer.getTitle(),
                    offer.getSlug()
                ));
            }
        }
        return list;
    }
}
