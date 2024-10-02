package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OfferDto {
    Integer amountFrom;
    Integer amountTo;
    String currency;
    String technology;
    String title;
    String link;
}
