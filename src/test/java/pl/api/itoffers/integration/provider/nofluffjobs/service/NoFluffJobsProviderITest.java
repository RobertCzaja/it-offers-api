package pl.api.itoffers.integration.provider.nofluffjobs.service;

import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.WireMockOrchestrator;
import pl.api.itoffers.integration.offer.OfferServiceITest;
import pl.api.itoffers.integration.offer.helper.OffersAssert;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.provider.nofluffjobs.fetcher.NoFluffJobsParameters;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsDetailsOfferRepository;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;
import pl.api.itoffers.provider.nofluffjobs.service.NoFluffJobsProvider;

/**
 * todo: Check what is checked in here, make common abstraction
 *
 * @see OfferServiceITest
 */
public class NoFluffJobsProviderITest extends AbstractITest {

  @Autowired private NoFluffJobsDetailsOfferRepository detailsRepository;
  @Autowired private NoFluffJobsListOfferRepository listRepository;
  @Autowired private NoFluffJobsParameters parameters;
  @Autowired private NoFluffJobsProvider noFluffJobsProvider;
  @Autowired private OfferRepository offerRepository;
  @Autowired private CategoryRepository categoryRepository;
  @Autowired private CompanyRepository companyRepository;
  @Autowired private OffersAssert offersAssert;

  @BeforeEach
  public void setUp() {
    super.setUp();
    detailsRepository
        .deleteAll(); // todo think about orchestrator which will keep it all those repositories
    // clean
    // todo OfferTestManager
    listRepository.deleteAll();
    offerRepository.deleteAll();
    categoryRepository.deleteAll();
    companyRepository.deleteAll();
  }

  @Test
  void shouldFetchDataFromExternalServiceAndSaveTheseInItOffersService() throws IOException {
    setUpMockedNoFluffJobsService();

    noFluffJobsProvider.fetch();

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
