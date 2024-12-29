package pl.api.itoffers.integration.provider.justjoinit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.integration.provider.justjoinit.inmemory.JustJoinItInMemoryConnector;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItOffersFetcher;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.JustJoinItOffersFetcherV2;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.PayloadFromJsonExtractor;

public class JustJoinItProviderV2ITest extends AbstractITest {

  @Autowired
  @Qualifier("v2")
  private JustJoinItOffersFetcher fetcher;

  @Autowired private JustJoinItRepository repository;
  private JustJoinItInMemoryConnector connector;
  private JustJoinItProvider provider;

  @BeforeEach
  public void setUp() {
    super.setUp();
    repository.deleteAll();
    this.connector = JustJoinItInMemoryConnector.create();
    this.provider =
        new JustJoinItProvider(
            new JustJoinItOffersFetcherV2(this.connector, new PayloadFromJsonExtractor()),
            this.repository);
  }

  @Test
  void shouldSaveAllFetchedJjitOffers() {
    connector.payloadPath = JustJoinItParams.ALL_LOCATIONS_PAYLOAD_B1_DECEMBER_PATH_JSON;

    provider.fetch("thatTechnologyNameDoesNotMatterSinceReturnedPayloadIsFixed", UUID.randomUUID());

    assertThat(repository.findAll()).hasSize(100);
  }
}
