package pl.api.itoffers.integration.provider.justjoinit;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.WireMockOrchestrator;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.OffersAssert;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.provider.justjoinit.infrastructure.JustJoinItParameters;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.service.OffersCollector;

public class OffersCollectorITest extends AbstractITest {
  @Autowired private OfferTestManager offerTestManager;
  @Autowired private OffersCollector jjitOffersCollector;
  @Autowired private OffersAssert offersAssert;
  @Autowired private OfferRepository offerRepository;
  @Autowired private JustJoinItParameters parameters;

  @BeforeEach
  public void setUp() {
    super.setUp();
    offerTestManager.clearAll();
  }

  @Test
  public void shouldSaveOffersToRelationalDataBaseWithoutDuplications() throws IOException {

    WireMockOrchestrator.pathWillReturn(
        parameters.getOffersPath("php"), JustJoinItParams.V2_ALL_LOCATIONS_PHP_DUPLICATED_1_HTML);
    jjitOffersCollector.fetch("php");

    WireMockOrchestrator.pathWillReturn(
        parameters.getOffersPath("php"), JustJoinItParams.V2_ALL_LOCATIONS_PHP_DUPLICATED_2_HTML);
    jjitOffersCollector.fetch("php");

    offersAssert.expects(6, 28, 6);
    OffersAssert.hasExpectedOfferModel(
        offerRepository.findAll().get(3),
        "php",
        "Senior PHP Developer - Symfony",
        "the-software-house-senior-php-developer---symfony-gliwice-php",
        "The Software House",
        7,
        JustJoinItDateTime.createFrom("2025-01-24T16:01:44.851Z").value,
        new OffersAssert.ExpectedSalary("b2b", "PLN", 15500, 24000, true));
  }
}
