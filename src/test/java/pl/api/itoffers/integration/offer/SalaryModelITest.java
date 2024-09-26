package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.offer.application.repository.SalaryRepository;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryAmount;
import pl.api.itoffers.offer.domain.SalaryId;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SalaryModelITest extends AbstractITest {

    @Autowired
    private OfferTestManager offerTestManager;
    @Autowired
    private SalaryRepository salaryRepository;
    private OfferBuilder builder;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.builder = offerTestManager.createOfferBuilder();
        offerTestManager.clearAll();
    }

    @Test
    public void shouldSaveSalary() {
        Offer offer1 = builder.offer("php", "phpUnit").saveAndGetOffer();
        Offer offer2 = builder.offer("java", "jUnit").saveAndGetOffer();

        SalaryId id1a = new SalaryId(offer1.getId(), "PLN");
        SalaryId id1b = new SalaryId(offer1.getId(), "EUR");
        SalaryId id2a = new SalaryId(offer2.getId(), "PLN");

        salaryRepository.saveAll(List.of(
            new Salary(id1a, new SalaryAmount(15000, 20000), "b2b", true),
            new Salary(id1b, new SalaryAmount(16000, 21000), "uop", true),
            new Salary(id2a, new SalaryAmount(23000, 26000), "b2b", false)
        ));

        assertThat(salaryRepository.findById(id1a)).isNotNull();
        assertThat(salaryRepository.findById(id1b)).isNotNull();
        assertThat(salaryRepository.findById(id2a)).isNotNull();
    }
}
