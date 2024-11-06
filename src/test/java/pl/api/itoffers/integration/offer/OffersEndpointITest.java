package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;

import static org.assertj.core.api.Assertions.assertThat;

public class OffersEndpointITest extends AbstractITest {

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
        assertThat("").isEmpty();
    }
}
