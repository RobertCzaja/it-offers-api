package pl.api.itoffers.integration.provider.justjoinit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.JustJoinItProviderFactory;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItProvider;
import pl.api.itoffers.report.service.ImportStatistics;

public class JustJoinItProviderV1ITest extends AbstractITest {

  @Autowired private JustJoinItRepository repository;
  @Autowired private JustJoinItProviderFactory justJoinItProviderFactory;
  @MockBean private ImportStatistics importStatistics;

  @BeforeEach
  @Override
  public void setUp() {
    super.setUp();
    repository.deleteAll();
  }

  @Test
  void shouldFetchAndSaveOffersFromExternalService() {

    JustJoinItProvider provider = justJoinItProviderFactory.create();

    UUID scrapingId = UUID.randomUUID();
    provider.fetch("thatTechnologyNameDoesNotMatterInThisTest", scrapingId);

    assertThat(repository.findAll()).hasSize(87);
  }
}
