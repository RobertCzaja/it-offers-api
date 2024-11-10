package pl.api.itoffers.offer.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.offer.application.dto.incoming.DatesRangeFilter;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;
import pl.api.itoffers.offer.application.factory.TechnologiesFilterFactory;
import pl.api.itoffers.offer.application.repository.OfferReadRepository;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class OfferController {
    public final static String PATH_OFFERS = "/offers";

    private final OfferReadRepository offerRepository;
    private final TechnologiesFilterFactory technologiesFilterFactory;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(PATH_OFFERS)
    public ResponseEntity<OffersDto> offers(
        @RequestParam(required = false) String[] technologies,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFrom,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo
    ) {
        DatesRangeFilter dates = new DatesRangeFilter(dateFrom, dateTo);
        return new ResponseEntity<OffersDto>(
            new OffersDto(offerRepository.getList(
                technologiesFilterFactory.get(technologies),
                dates.getFrom(),
                dates.getTo()
            )),
            HttpStatus.OK
        );
    }
}
