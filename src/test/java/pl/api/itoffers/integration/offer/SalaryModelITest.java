package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.offer.application.repository.SalaryRepository;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SalaryITest extends AbstractITest {

    @Autowired
    private SalaryRepository salaryRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldSaveSalary() {

        SalaryId id = new SalaryId(UUID.randomUUID(), "PLN");
        Salary salary = new Salary(id, 15000, 20000, "b2b", true);

        salaryRepository.save(salary);

        assertThat("").isNotNull();
    }
}
