package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

/** todo name to change #59 */
@Value
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OffersDto2 {
    List<OfferDto2> list;
}
