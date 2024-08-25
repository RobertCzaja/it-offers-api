package pl.api.itoffers.integration.provider.justjoinit;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.integration.provider.justjoinit.inmemory.JustJoinItInMemoryConnector;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItPayloadExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class JustJoinItProviderITest extends AbstractITest {

    @Autowired
    private JustJoinItPayloadExtractor payloadExtractor;
    @Autowired
    private JustJoinItRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    /* TODO this test does not involve Postgres at all - think about then introduce new AbstractClass than the current one */
    @Test
    void shouldFetchAndSaveOffersFromExternalService() {

        JustJoinItProvider provider = new JustJoinItProvider(
                new JustJoinItInMemoryConnector(new FileManager()),
                payloadExtractor,
                repository
        );

        UUID scrapingId = UUID.randomUUID();
        provider.fetch("thatTechnologyNameDoesNotMatterInThisTest", scrapingId);

        assertThat(repository.findAll()).hasSize(87);
    }
}
