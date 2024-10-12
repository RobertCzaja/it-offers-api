package pl.api.itoffers.integration.offer.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.*;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;


import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferModelITest extends AbstractITest {

    @Autowired
    private OfferTestManager offerTestManager;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
        offerTestManager.clearAll();
    }

    @Test
    public void shouldSaveOfferEntity() {

        Set<Category> categories = saveAndGetCategories();
        LocalDateTime publishedAt = JustJoinItDateTime.createFrom().value;
        Company company = saveAndGetCompany();

        Offer offer = new Offer(
                "php",
                "remitly-software-development-engineer-krakow-go-5fbdbda0",
                "Software Development Engineer",
                "mid",
                new Characteristics("hybrid","full_time", true),
                categories,
                new HashSet<>(),
                company,
                publishedAt,
                JustJoinItDateTime.createFrom().value
        );

        offerRepository.save(offer);

        Optional<Offer> fetchedOffer = offerRepository.findById(offer.getId());
        assertThat(fetchedOffer.get().getCreatedAt()).isEqualTo(JustJoinItDateTime.createFrom().value);
        assertThat(fetchedOffer.get().getSalaries()).isEmpty();
    }

    private Set<Category> saveAndGetCategories() {
        Category category1 = new Category("php");
        Category category2 = new Category("mysql");
        Category category3 = new Category("git");
        categoryRepository.saveAll(List.of(category1, category2, category3));
        Set<Category> categories = new HashSet<Category>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        return categories;
    }

    private Company saveAndGetCompany() {
        Company company = new Company("creativestyle", "Kraków", "Zabłocie 25/1");
        companyRepository.save(company);
        return company;
    }
}
