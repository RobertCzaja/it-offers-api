package pl.api.itoffers.provider.justjoinit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;
import pl.api.itoffers.offer.application.service.OfferService;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FetchJustJoinItOffersService {

    @Autowired
    private TechnologyRepository technologyRepository;
    @Autowired
    private JustJoinItProvider justJoinItProvider;
    @Autowired
    private OfferService offerService;

    public void fetch(String requestedTechnology) {
        List<String> technologies = requestedTechnology.isEmpty()
                ? technologyRepository.allActive()
                : Arrays.asList(requestedTechnology);

        UUID scrapingId = UUID.randomUUID();

        for (String technology : technologies) {
            log.info(String.format("[just-join-it] fetching offers from technology: %s", technology));
            justJoinItProvider.fetch(technology, scrapingId);
        }

        offerService.processOffersFromExternalService(scrapingId);
    }
}
