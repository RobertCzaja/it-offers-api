package pl.api.itoffers.provider.nofluffjobs.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.provider.nofluffjobs.factory.NoFluffJobsProviderImporterFactory;

@RestController
@RequiredArgsConstructor
public class FetchOffersController {

  private final NoFluffJobsProviderImporterFactory importerFactory;

  @PostMapping("/no-fluff-jobs/fetch")
  public void fetch() {
    importerFactory.create().importOffers(null);
  }
}
