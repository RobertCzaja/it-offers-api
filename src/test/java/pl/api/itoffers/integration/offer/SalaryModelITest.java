package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.offer.application.repository.SalaryRepository;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryAmount;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        salaryRepository.saveAll(List.of(
            new Salary(offer1.getId(), new SalaryAmount(15000, 20000, "PLN"), "b2b", true),
            new Salary(offer1.getId(), new SalaryAmount(16000, 21000, "EUR"), "uop", true),
            new Salary(offer2.getId(), new SalaryAmount(23000, 26000, "PLN"), "b2b", false)
        ));

        assertThat(salaryRepository.findAll()).hasSize(3);
    }

    @Test()
    public void shouldNotAllowToSaveSalaryConsideredAsDuplication() {
        Offer offer1 = builder.offer("php", "phpUnit").saveAndGetOffer();

        salaryRepository.save(new Salary(offer1.getId(), new SalaryAmount(15000, 20000, "PLN"), "b2b", true));

        assertThrows(
            DataIntegrityViolationException.class,
            () -> salaryRepository.save(
                new Salary(offer1.getId(), new SalaryAmount(16000, 21000, "PLN"), "b2b", false)
            )
        );
    }
}
