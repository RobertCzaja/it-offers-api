package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.offer.application.OfferRepository;
import pl.api.itoffers.offer.application.service.OfferService;
import pl.api.itoffers.offer.domain.Offer;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferModelITest extends AbstractITest {

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void shouldSaveOfferEntity() {

        Offer offer = new Offer("some-slug");

        offerRepository.save(offer);

        assertThat(offerRepository.findById(offer.getId())).isNotNull();
    }
}
