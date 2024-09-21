package pl.api.itoffers.integration.offer;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.JustJoinItProviderFactory;
import pl.api.itoffers.integration.provider.justjoinit.inmemory.JustJoinItInMemoryConnector;
import pl.api.itoffers.integration.provider.justjoinit.payload.JustJoinItParams;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.application.service.OfferService;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferServiceITest extends AbstractITest {

    private static final String TECHNOLOGY = "php";

    @Autowired
    private OfferService offerService;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JustJoinItProviderFactory justJoinItProviderFactory;
    @Autowired
    private JustJoinItRepository jjitRawOffersRepository;
    private JustJoinItInMemoryConnector jjitConnector;
    private JustJoinItProvider jjitProvider;

    @BeforeEach
    public void setUp() {
        super.setUp();
        jjitRawOffersRepository.deleteAll();
        offerRepository.deleteAll();
        categoryRepository.deleteAll();
        companyRepository.deleteAll();
        this.jjitConnector = JustJoinItInMemoryConnector.create();
        this.jjitProvider = justJoinItProviderFactory.create(jjitConnector);
    }

    @Test
    public void shouldSaveOffersToRelationalDataBaseWithoutDuplications() {

        UUID scrappingId1 = UUID.randomUUID();
        fetchOffersFromExternalService(scrappingId1, JustJoinItParams.ALL_LOCATIONS_PAYLOAD_A1_PATH); // all new Offers

        UUID scrappingId2 = UUID.randomUUID();
        fetchOffersFromExternalService(scrappingId2, JustJoinItParams.ALL_LOCATIONS_PAYLOAD_A2_PATH); // duplicates two Offers

        offerService.processOffersFromExternalService(scrappingId1);
        offerService.processOffersFromExternalService(scrappingId2);

        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(7);

        Set<String> categoryNames = new HashSet<>();
        categoryRepository.findAll().forEach((category) -> categoryNames.add(category.getName()));
        assertThat(categoryNames).hasSize(25);

        Set<String> companyNames = new HashSet<>();
        companyRepository.findAll().forEach((company) -> companyNames.add(company.getName()));
        assertThat(companyNames).hasSize(7);

        Offer offer =offers.get(0);
        assertThat(offer.getTechnology()).isEqualTo("php");
        assertThat(offer.getTitle()).isEqualTo("Senior Full Stack Developer (React & PHP)");
        assertThat(offer.getSlug()).isEqualTo("iteamly-senior-full-stack-developer-react-php--krakow-php");
        assertThat(offer.getCompany().getName()).isEqualTo("iTeamly");
        assertThat(offer.getPublishedAt()).isEqualTo(JustJoinItDateTime.createFrom("2024-08-25T07:00:56.216Z").value);
    }

    private void fetchOffersFromExternalService(UUID scrappingId, String returnedPayload) {
        jjitConnector.payloadPath = returnedPayload;
        jjitProvider.fetch(TECHNOLOGY, scrappingId);
    }
}
