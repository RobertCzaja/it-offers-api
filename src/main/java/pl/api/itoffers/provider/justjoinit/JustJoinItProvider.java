package pl.api.itoffers.provider.justjoinit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItOffersFetcher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JustJoinItProvider {

    private final JustJoinItOffersFetcher justJoinItOffersFetcher;
    private final JustJoinItRepository repository;

    public void fetch(String technology, UUID scrapingId)
    {
        ArrayList<Map<String, Object>> offers = justJoinItOffersFetcher.fetch(technology);
        offers.forEach(offer -> repository.save(new JustJoinItRawOffer(scrapingId,technology,offer,LocalDateTime.now())));
    }

}
