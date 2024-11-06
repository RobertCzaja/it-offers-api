package pl.api.itoffers.offer.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto2;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto2;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferController {
    public final static String PATH_OFFERS = "/offers";

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(PATH_OFFERS)
    public ResponseEntity<OffersDto2> offers(
        @RequestParam(required = false) String[] technologies,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFrom,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo
    ) {
        // todo add real implementation #59
        OffersDto2 result = new OffersDto2(List.of(
            new OfferDto2(1,2,"PLN", "php", "title1", "link1"),
            new OfferDto2(2,3,"PLN", "java", "title2", "link2")
        ));
        return new ResponseEntity<OffersDto2>(result, HttpStatus.OK);
    }
}
