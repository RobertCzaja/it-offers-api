package pl.api.itoffers.offer.application.service;

import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportSalariesService {

    /*todo change return type*/
    public List<OfferDto> getMostPaidOffers() {
        List<OfferDto> offers = new ArrayList<OfferDto>();
        offers.add(new OfferDto(15000,17000,"PLN", "php", "some title", "some url"));
        return offers;
    }
}
