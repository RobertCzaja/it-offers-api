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
    /**
     * todo useless layer of an abstraction, to remove todo use only:
     *
     * @see TechnologyOffersCollector
     */
    technologyRepository.allActive().forEach(collector::fetchOffers);
  }
}
