package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class OfferDto {
    Integer amountFrom;
    Integer amountTo;
    String currency;
    String technology;
    String title;
    String link;
}
