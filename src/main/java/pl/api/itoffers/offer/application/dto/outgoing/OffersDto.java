package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.*;

import java.util.List;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OffersDto {
    List<OfferDto> list;
}
