package pl.api.itoffers.integration.provider.nofluffjobs.fetcher;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.WireMockOrchestrator;
import pl.api.itoffers.provider.nofluffjobs.fetcher.NoFluffJobsParameters;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.NoFluffJobsDetailsProvider;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsDetailsOfferRepository;

public class NoFluffJobsDetailsProviderITest extends AbstractITest {

  @Autowired private NoFluffJobsDetailsProvider provider;
  @Autowired private NoFluffJobsDetailsOfferRepository repository;
  @Autowired private NoFluffJobsParameters parameters;

  @BeforeEach
  @Override
  public void setUp() {
    super.setUp();
    repository.deleteAll();
  }

  @Test
  void shouldCorrectlyFetchAndSaveDetailsOfferInMongoDB() throws IOException {

    String slug = "senior-java-developer-boldare-west-pomeranian-1";
    UUID scrapingId = UUID.randomUUID();
    UUID listOfferId = UUID.randomUUID();
    WireMockOrchestrator.pathWillReturn(
        parameters.detailsPath(slug), NoFluffJobsParams.DETAILS_JAVA_HTML_PATH);

    provider.fetch(slug, scrapingId, listOfferId);

    assertThat(repository.findAll()).hasSize(1);
    assertThat(repository.findByOfferId(listOfferId).get().getOffer()).hasSize(16);
  }
}
