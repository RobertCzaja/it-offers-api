package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class OfferSalaryDto {
    Integer from;
    Integer to;
    String currency;
    String employmentType;
    Boolean isOriginal;
}
