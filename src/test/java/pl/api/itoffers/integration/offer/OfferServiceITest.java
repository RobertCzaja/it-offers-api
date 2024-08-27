package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.JustJoinItProviderFactory;
import pl.api.itoffers.integration.provider.justjoinit.inmemory.JustJoinItInMemoryConnector;
import pl.api.itoffers.integration.provider.justjoinit.payload.JustJoinItParams;
import pl.api.itoffers.offer.application.service.OfferService;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferServiceITest extends AbstractITest {

    private static final String TECHNOLOGY = "thatTechnologyDoesntMatterInThatTest";

    @Autowired
    private OfferService offerService;
    @Autowired
    private JustJoinItProviderFactory justJoinItProviderFactory;
    @Autowired
    private JustJoinItRepository jjitRawOffersRepository; // todo to remove - needed only for creating this Integration Test
    private JustJoinItInMemoryConnector jjitConnector;
    private JustJoinItProvider jjitProvider;

    @BeforeEach
    public void setUp() {
        super.setUp();
        jjitRawOffersRepository.deleteAll();
        this.jjitConnector = JustJoinItInMemoryConnector.create();
        this.jjitProvider = justJoinItProviderFactory.create(jjitConnector);
    }

    @Test
    public void shouldSaveOffersToRelationalDataBaseWithoutDuplications() {

        UUID scrappingId1 = UUID.randomUUID();
        fetchOffersFromExternalService(scrappingId1, JustJoinItParams.ALL_LOCATIONS_PAYLOAD_A1_PATH); // all new Offers

        UUID scrappingId2 = UUID.randomUUID();
        fetchOffersFromExternalService(scrappingId2, JustJoinItParams.ALL_LOCATIONS_PAYLOAD_A2_PATH); // duplicates two Offers

        offerService.processOffersFromExternalService(scrappingId1);
        offerService.processOffersFromExternalService(scrappingId2);

        /* TODO Assertions: check for unique: Offers/Categories/Companies */
        assertThat("").isNotNull();
    }

    private void fetchOffersFromExternalService(UUID scrappingId, String returnedPayload) {
        jjitConnector.payloadPath = returnedPayload;
        jjitProvider.fetch(TECHNOLOGY, scrappingId);
    }
}
