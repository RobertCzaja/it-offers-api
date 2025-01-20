package pl.api.itoffers.provider.nofluffjobs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoFluffJobsProvider {

  private final TechnologyOffersCollector collector;
  private final TechnologyRepository technologyRepository;

  public void fetch() {
    technologyRepository
        .allActive()
        .forEach(
            technology -> {
              log.info("[{}] start import", technology);
              collector.fetchOffers(technology);
              log.info("[{}] import successfully delegated", technology);
            });
  }
}
