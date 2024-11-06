package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.OffersEndpointCaller;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto2;

import static org.assertj.core.api.Assertions.assertThat;

public class OffersEndpointITest extends AbstractITest {

    @Autowired
    private OffersEndpointCaller caller;
    @Autowired
    private OfferTestManager offerTestManager;
    private OfferBuilder builder;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.builder = offerTestManager.createOfferBuilder();
        this.builder.notGenerateEntityIdsBecauseTheseShouldBeGeneratedByJPA();
        offerTestManager.clearAll();
    }

    @Test
    public void shouldCorrectlyGetOffers() {

        ResponseEntity<OffersDto2> response = caller.makeRequest(null, null, null ,null);

        assertThat("").isEmpty(); // todo add real assertions
    }
}
