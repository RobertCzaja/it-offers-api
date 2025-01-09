package pl.api.itoffers.integration.provider.nofluffjobs;

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
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.NoFluffJobsListProvider;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

public class NoFluffJobsListProviderITest extends AbstractITest {

  private static final String TECHNOLOGY = "java";
  @Autowired private NoFluffJobsListProvider noFluffJobsListProvider;
  @Autowired private NoFluffJobsParameters parameters;
  @Autowired private NoFluffJobsListOfferRepository repository;

  @BeforeEach
  public void setUp() {
    super.setUp();
    repository.deleteAll();
  }

  @Test
  void shouldCorrectlyFetchAndSaveListOffersInMongoDB() throws IOException {

    WireMockOrchestrator.pathWillReturn(
        parameters.listPath(TECHNOLOGY), NoFluffJobsParams.LIST_JAVA_HTML_PATH);

    noFluffJobsListProvider.fetch(TECHNOLOGY, UUID.randomUUID());

    assertThat(repository.findAll()).hasSize(20);
  }
}
