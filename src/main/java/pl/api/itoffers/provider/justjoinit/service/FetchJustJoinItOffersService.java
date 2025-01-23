package pl.api.itoffers.provider.justjoinit.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;
import pl.api.itoffers.offer.application.service.OfferService;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class FetchJustJoinItOffersService {
  private final TechnologyRepository technologyRepository;
  private final JustJoinItProvider justJoinItProvider;
  private final OfferService offerService;

  public void fetch(@NotNull String requestedTechnology) {
    List<String> technologies =
        requestedTechnology.isEmpty()
            ? technologyRepository.allActive()
            : Arrays.asList(requestedTechnology);

    UUID scrapingId = UUID.randomUUID();

    try {
      for (String technology : technologies) {
        log.info(String.format("[jjit] fetching category: %s", technology));
        justJoinItProvider.fetch(technology, scrapingId);
      }

      offerService.processOffersFromExternalService(scrapingId);
    } catch (Exception e) {
      log.error("Error on fetching JustJoinIT offers", e);
      throw e;
    }
  }
}
