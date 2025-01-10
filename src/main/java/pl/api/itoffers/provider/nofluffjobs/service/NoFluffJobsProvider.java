package pl.api.itoffers.provider.nofluffjobs.service;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoFluffJobsProvider {

  private final NoFluffJobsSingleTechnologyOfferWorker worker;

  /** TODO under construction */
  public void fetch() {

    String[] technologies = {"java", "php"};

    Arrays.stream(technologies)
        .forEach(
            technology -> {
              log.info("[{}] start import", technology);
              worker.fetchOffers(technology);
              log.info("[{}] import successfully delegated", technology);
            });
  }
}
