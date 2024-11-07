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
import pl.api.itoffers.shared.utils.json.Json;

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
        this.builder.job("java").at("11-01").skills("java", "maven").pln(26500, 30000).save();
        this.builder.job("php").at("10-31").skills("php", "docker").pln(15000, 18000).save();
        this.builder.job("php").at("11-02").skills("php", "kubernetes").pln(15500, 19000).save();
        this.builder.job("java").at("11-03").skills("java", "junit").pln(25000, 29000).save();

        ResponseEntity<OffersDto2> response = caller.makeRequest(null, null, null ,null);

        assertThat(response.getBody().getList()).hasSize(4);
        // todo add detailed assertions
    }
}
