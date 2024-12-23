package pl.api.itoffers.integration.provider.justjoinit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.JustJoinItProviderFactory;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class JustJoinItProviderV1ITest extends AbstractITest {

    @Autowired
    private JustJoinItRepository repository;
    @Autowired
    private JustJoinItProviderFactory justJoinItProviderFactory;

    @BeforeEach
    public void setUp() {
        super.setUp();
        repository.deleteAll();
    }

    @Test
    void shouldFetchAndSaveOffersFromExternalService() {

        JustJoinItProvider provider = justJoinItProviderFactory.create();

        UUID scrapingId = UUID.randomUUID();
        provider.fetch("thatTechnologyNameDoesNotMatterInThisTest", scrapingId);

        assertThat(repository.findAll()).hasSize(87);
    }
}
