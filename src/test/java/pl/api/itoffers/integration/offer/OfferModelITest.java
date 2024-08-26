package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Offer;


import static org.assertj.core.api.Assertions.assertThat;

public class OfferModelITest extends AbstractITest {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void shouldSaveOfferEntity() {

        categoryRepository.save(new Category("php"));
        categoryRepository.save(new Category("mysql"));
        categoryRepository.save(new Category("git"));

        Offer offer = new Offer(
                "remitly-software-development-engineer-krakow-go-5fbdbda0",
                "Software Development Engineer"
        );

        offerRepository.save(offer);

        assertThat(offerRepository.findById(offer.getId())).isNotNull();
    }
}
