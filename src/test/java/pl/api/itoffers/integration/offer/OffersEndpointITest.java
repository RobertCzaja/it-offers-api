package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.OffersAssert;
import pl.api.itoffers.integration.offer.helper.OffersEndpointCaller;
import pl.api.itoffers.integration.offer.helper.ReportAssert;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OffersEndpointITest extends AbstractITest {

    @Autowired
    private OffersEndpointCaller caller;
    @Autowired
    private OfferTestManager offerTestManager;
    private OfferBuilder builder;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.builder = offerTestManager.createOfferBuilder();
        this.builder.notGenerateEntityIdsBecauseTheseShouldBeGeneratedByJPA();
        offerTestManager.clearAll();
    }

    @Test
    public void shouldCorrectlyGetOffers() {
        saveOffersInDb();

        ResponseEntity<OffersDto> response = caller.makeRequest(null, null ,null);

        assertThat(response.getBody().getList()).hasSize(4);
        List<List> expected = List.of(
            List.of("java", List.of("java", "junit"), "2024-11-03"),
            List.of("php", List.of("php", "kubernetes"), "2024-11-02"),
            List.of("java", List.of("java", "maven"), "2024-11-01"),
            List.of("php", List.of("php", "docker"), "2024-10-31")
        );
        OffersAssert.hasExactOffers(expected, response.getBody());
    }

    @Test
    public void shouldFilterOffers() {
        saveOffersInDb();

        ResponseEntity<OffersDto> response = caller.makeRequest(List.of("php"), "2024-11-01" ,"2024-11-02");

        assertThat(response.getBody().getList()).hasSize(1);
        List<List> expected = List.of(
            List.of("php", List.of("php", "kubernetes"), "2024-11-02")
        );
        OffersAssert.hasExactOffers(expected, response.getBody());
    }

    @ParameterizedTest
    @CsvSource(value = {
        "2024-09-01:2024-08-01",
        "2026-01-01:"
    }, delimiter = ':')
    public void shouldCheckDatesQueryParamsCorrection(String dateFrom, String dateTo) {
        ResponseEntity<OffersDto> response = caller.makeRequest(null, dateFrom ,dateTo);

        assertThat(response.getStatusCode()).isEqualTo( HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getList()).isNull();
    }

    @Test
    public void shouldReturnEmptyListWhenThereIsNoOfferToReturn() {
        ResponseEntity<OffersDto> response = caller.makeRequest(null, null ,null);

        assertThat(response.getBody().getList()).isEmpty();
    }

    @Test
    public void shouldUserDifferentThanAdminIsNotAllowToGetOffers() {
        ResponseEntity<String> response = caller.makeRequestAsUser();

        ReportAssert.responseIs(response, HttpStatus.FORBIDDEN, "Access denied");
    }

    public void saveOffersInDb() {
        this.builder.job("java").at("11-01").skills("java", "maven").pln(26500, 30000).save();
        this.builder.job("php").at("10-31").skills("php", "docker").pln(15000, 18000).save();
        this.builder.job("php").at("11-02").skills("php", "kubernetes").pln(15500, 19000).save();
        this.builder.job("java").at("11-03").skills("java", "junit").pln(25000, 29000).save();
    }
}
