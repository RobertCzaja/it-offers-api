package pl.api.itoffers.integration.provider.nofluffjobs.service;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.WireMockOrchestrator;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.OffersAssert;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;
import pl.api.itoffers.provider.nofluffjobs.fetcher.NoFluffJobsParameters;
import pl.api.itoffers.provider.nofluffjobs.service.NoFluffJobsOffersCollector;

public class NoFluffJobsOffersCollectorITest extends AbstractITest {

  @Autowired private NoFluffJobsParameters parameters;
  @Autowired private OfferRepository offerRepository;
  @Autowired private OffersAssert offersAssert;
  @Autowired private OfferTestManager offerTestManager;
  @Autowired private NoFluffJobsOffersCollector collector;
  @MockBean private TechnologyRepository technologyRepository;

  @BeforeEach
  public void setUp() {
    super.setUp();
    offerTestManager.clearAll();
  }

  @Test
  void shouldFetchDataFromExternalServiceAndSaveTheseInItOffersService() throws IOException {
    setUpMockedNoFluffJobsService();
    when(technologyRepository.allActive()).thenReturn(List.of("java", "php"));

    collector.collectFromProvider("");

    offersAssert.expects(4, 22, 3);
    OffersAssert.hasExpectedOfferModel(
        offerRepository.findAll().get(0),
        "java",
        "Senior Software Engineer Salon Ops",
        "senior-software-engineer-salon-ops-phorest-remote",
        "Phorest",
        6,
        LocalDateTime.of(2025, 1, 13, 18, 30, 29, 545000000),
        new OffersAssert.ExpectedSalary("b2b", "PLN", 24901, 24901, true));
  }

  private void setUpMockedNoFluffJobsService() throws IOException {
    WireMockOrchestrator.pathWillReturn(
        parameters.listPath("php"), NoFluffJobsParams.B1_E2E_PHP_LIST);
    WireMockOrchestrator.pathWillReturn(
        parameters.listPath("java"), NoFluffJobsParams.B1_E2E_JAVA_LIST);
    WireMockOrchestrator.pathWillReturn(
        parameters.detailsPath(NoFluffJobsParams.B1_E2E_JAVA_1_SLUG),
        NoFluffJobsParams.B1_E2E_JAVA_1_DETAILS);
    WireMockOrchestrator.pathWillReturn(
        parameters.detailsPath(NoFluffJobsParams.B1_E2E_JAVA_2_SLUG),
        NoFluffJobsParams.B1_E2E_JAVA_2_DETAILS);
    WireMockOrchestrator.pathWillReturn(
        parameters.detailsPath(NoFluffJobsParams.B1_E2E_PHP_1_SLUG),
        NoFluffJobsParams.B1_E2E_PHP_1_DETAILS);
    WireMockOrchestrator.pathWillReturn(
        parameters.detailsPath(NoFluffJobsParams.B1_E2E_PHP_2_SLUG),
        NoFluffJobsParams.B1_E2E_PHP_2_DETAILS);
  }
}
