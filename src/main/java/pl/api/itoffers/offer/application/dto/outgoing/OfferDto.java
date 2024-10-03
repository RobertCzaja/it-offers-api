package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
public class OfferDto {
    Integer amountFrom;
    Integer amountTo;
    String currency;
    String technology;
    String title;
    String link;

    public static List<OfferDto> createFrom(Offer offer) {
        List<OfferDto> list = new ArrayList<OfferDto>();
        for (Salary salary : offer.getSalaries()) {
            list.add(new OfferDto(
                salary.getAmount().getFrom(),
                salary.getAmount().getTo(),
                salary.getAmount().getCurrency(),
                offer.getTechnology(),
                offer.getTitle(),
                offer.getSlug()
            ));
        }
        return list;
    }
}
