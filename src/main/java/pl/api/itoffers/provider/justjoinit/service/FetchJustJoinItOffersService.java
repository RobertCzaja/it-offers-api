package pl.api.itoffers.provider.justjoinit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;
import pl.api.itoffers.offer.application.service.OfferService;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.shared.logger.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FetchJustJoinItOffersService {
    private final TechnologyRepository technologyRepository;
    private final JustJoinItProvider justJoinItProvider;
    private final OfferService offerService;
    private final Logger logger;

    public void fetch(String requestedTechnology) {
        List<String> technologies = requestedTechnology.isEmpty()
                ? technologyRepository.allActive()
                : Arrays.asList(requestedTechnology);

        UUID scrapingId = UUID.randomUUID();

        logger.info("import-jjit", "start fetching");
        for (String technology : technologies) {
            log.info(String.format("[just-join-it] fetching offers from technology: %s", technology));
            justJoinItProvider.fetch(technology, scrapingId);
        }
        logger.info("import-jjit", "fetched successfully");

        offerService.processOffersFromExternalService(scrapingId);
        logger.info("import-jjit", "imported successfully");
    }
}
