package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;


import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferModelITest extends AbstractITest {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void shouldSaveOfferEntity() {

        Set<Category> categories = saveAndGetCategories();
        LocalDateTime publishedAt = JustJoinItDateTime.createFrom("yyyy-MM-dd'T'HH:mm:ss.SSSX").value;
        Company company = saveAndGetCompany();

        Offer offer = new Offer(
                "remitly-software-development-engineer-krakow-go-5fbdbda0",
                "Software Development Engineer",
                "mid",
                new Salary(Double.valueOf(14000),Double.valueOf(18000), "PLN", "b2b"), // todo change to real one
                categories,
                company,
                publishedAt
        );

        offerRepository.save(offer);

        Optional<Offer> fetchedOffer = offerRepository.findById(offer.getId());
        assertThat(fetchedOffer.get()).isNotNull();
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
