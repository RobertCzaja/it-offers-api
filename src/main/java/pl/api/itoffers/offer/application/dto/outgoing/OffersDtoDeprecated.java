package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.*;

import java.util.List;

/** @deprecated todo remove in #59 */
@Value
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OffersDtoDeprecated {
    List<OfferDtoDeprecated> list;
}
