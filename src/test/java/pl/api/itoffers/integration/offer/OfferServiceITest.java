package pl.api.itoffers.integration.offer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.JustJoinItProviderFactory;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.OffersAssert;
import pl.api.itoffers.integration.provider.justjoinit.inmemory.JustJoinItInMemoryConnector;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.application.service.OfferService;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;

/**
 * @deprecated todo to remove
 */
public class OfferServiceITest extends AbstractITest {

  private static final String TECHNOLOGY = "php";

  @Autowired private OfferTestManager offerTestManager;
  @Autowired private OfferService offerService;
  @Autowired private OfferRepository offerRepository;
  @Autowired private JustJoinItProviderFactory justJoinItProviderFactory;
  @Autowired private JustJoinItRepository jjitRawOffersRepository;
  @Autowired private OffersAssert offersAssert;
  private JustJoinItInMemoryConnector jjitConnector;
  private JustJoinItProvider jjitProvider;

  @BeforeEach
  public void setUp() {
    super.setUp();
    offerTestManager.clearAll();
    this.jjitConnector = JustJoinItInMemoryConnector.create();
    this.jjitProvider = justJoinItProviderFactory.create(jjitConnector);
  }

  @Test
  public void shouldSaveOffersToRelationalDataBaseWithoutDuplications() {

    UUID scrappingId1 = UUID.randomUUID();
    fetchOffersFromExternalService(
        scrappingId1, JustJoinItParams.ALL_LOCATIONS_PAYLOAD_A1_PATH); // all new Offers

    UUID scrappingId2 = UUID.randomUUID();
    fetchOffersFromExternalService(
        scrappingId2, JustJoinItParams.ALL_LOCATIONS_PAYLOAD_A2_PATH); // duplicates two Offers

    offerService.processOffersFromExternalService(scrappingId1);
    offerService.processOffersFromExternalService(scrappingId2);

    offersAssert.expects(7, 25, 7);
    OffersAssert.hasExpectedOfferModel(
        offerRepository.findAll().get(0),
        "php",
        "Senior Full Stack Developer (React & PHP)",
        "iteamly-senior-full-stack-developer-react-php--krakow-php",
        "iTeamly",
        3,
        JustJoinItDateTime.createFrom("2024-08-25T07:00:56.216Z").value,
        new OffersAssert.ExpectedSalary("b2b", "PLN", 26000, 33000, true),
        new OffersAssert.ExpectedSalary("permanent", "USD", 6553, 8388, true),
        new OffersAssert.ExpectedSalary("permanent", "PLN", 25000, 32000, false));
  }

  @Test
  public void
      shouldSaveOfferOnlyOnceEvenIfTheNewOneWasUpdatedInJustJoinItExternalServiceWhichWeKnowBecauseOfNewPublishedAtField() {
    UUID scrappingId1 = UUID.randomUUID();
    fetchOffersFromExternalService(
        scrappingId1, JustJoinItParams.ALL_LOCATIONS_PAYLOAD_DUPLICATED_1_PATH);

    UUID scrappingId2 = UUID.randomUUID();
    fetchOffersFromExternalService(
        scrappingId2, JustJoinItParams.ALL_LOCATIONS_PAYLOAD_DUPLICATED_2_PATH);

    UUID scrappingId3 = UUID.randomUUID();
    fetchOffersFromExternalService(
        scrappingId3, JustJoinItParams.ALL_LOCATIONS_PAYLOAD_DUPLICATED_3_PATH);

    offerService.processOffersFromExternalService(scrappingId1);
    offerService.processOffersFromExternalService(scrappingId2);
    offerService.processOffersFromExternalService(scrappingId3);

    List<Offer> offers = offerRepository.findAll();
    assertThat(offers).hasSize(1);
    Offer offer = offers.get(0);
    assertThat(offer.getPublishedAt())
        .isEqualTo(JustJoinItDateTime.createFrom("2024-08-25T07:00:56.216Z").value);
  }

  private void fetchOffersFromExternalService(UUID scrappingId, String returnedPayload) {
    jjitConnector.payloadPath = returnedPayload;
    jjitProvider.fetch(TECHNOLOGY, scrappingId);
  }
}
