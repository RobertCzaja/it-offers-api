package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.ReportAssert;
import pl.api.itoffers.integration.offer.helper.ReportSalariesEndpointCaller;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;
import pl.api.itoffers.shared.utils.json.Json;


import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureJsonTesters
public class ReportSalaryEndpointResultITest extends AbstractITest {

    @Autowired
    private JacksonTester<OffersDto> jsonResultAttempt;
    @Autowired
    private ReportSalariesEndpointCaller caller;
    @Autowired
    private OfferTestManager offerTestManager;
    private OfferBuilder builder;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.builder = offerTestManager.createOfferBuilder();
        this.builder.generateId = false;
        offerTestManager.clearAll();
    }

    @Test
    public void shouldReturnMostTopPaidJobs() {
        this.builder.plainJob("php").pln(15000, 18000).save();
        this.builder.plainJob("php").pln(17000, 21000).save();
        this.builder.plainJob("php").pln(18000, 23000, "b2b").pln(15000, 19900, "permanent").save();
        this.builder.plainJob("java").pln(17000, 19000).save();
        this.builder.plainJob("java").pln(18000, 24000).save();
        this.builder.plainJob("java").pln(21500, 26000).usd(14000, 20100).save();
        this.builder.plainJob("java").usd(22000, 23000).save();

        HttpEntity<OffersDto> result = caller.makeRequest(20000, null, null, null);

        assertThat(result.getBody().getList()).hasSize(4);
        assertThat(Json.convertToString(result.getBody())).isEqualTo("{\"list\":["+
            "{\"amountFrom\":21500,\"amountTo\":26000,\"currency\":\"PLN\",\"technology\":\"java\",\"title\":\"Software Development Engineer\",\"link\":\"remitly-software-development-engineer-krakow-go-5fbdbda0\"},"+
            "{\"amountFrom\":18000,\"amountTo\":24000,\"currency\":\"PLN\",\"technology\":\"java\",\"title\":\"Software Development Engineer\",\"link\":\"remitly-software-development-engineer-krakow-go-5fbdbda0\"},"+
            "{\"amountFrom\":18000,\"amountTo\":23000,\"currency\":\"PLN\",\"technology\":\"php\",\"title\":\"Software Development Engineer\",\"link\":\"remitly-software-development-engineer-krakow-go-5fbdbda0\"},"+
            "{\"amountFrom\":17000,\"amountTo\":21000,\"currency\":\"PLN\",\"technology\":\"php\",\"title\":\"Software Development Engineer\",\"link\":\"remitly-software-development-engineer-krakow-go-5fbdbda0\"}"+
        "]}");
    }

    @Test
    public void shouldUserDifferentThanAdminIsNotAllowToGetBestOffersSalariesReport() {
        ResponseEntity<String> response = caller.makeRequestAsUser();

        ReportAssert.responseIs(response, HttpStatus.FORBIDDEN, "Access denied");
    }

    @Test
    public void shouldReturnOnlyMostTopPaidPhpJobs() throws IOException {
        this.builder.plainJob("php").pln(15000, 18000).save();
        this.builder.plainJob("java").pln(17000, 19000).save();

        HttpEntity<OffersDto> result = caller.makeRequest(null, List.of("php"), null, null);

        OffersDto expected = new OffersDto(List.of(createOffer(15000, 18000)));
        assertThat(Json.convertToString(result.getBody())).isEqualTo(jsonResultAttempt.write(expected).getJson());
    }

    @Test
    public void shouldReturnOnlyMostTopPaidJobsForGivenDatesRange() throws IOException {
        this.builder.plainJob("php").pln(15000, 18000).at("01-01").save();
        this.builder.plainJob("php").pln(16000, 19000).at("01-02").save();
        this.builder.plainJob("php").pln(17000, 20000).at("01-03").save();
        this.builder.plainJob("php").pln(18000, 21000).at("01-04").save();

        HttpEntity<OffersDto> result = caller.makeRequest(
            null,
            null,
            "2024-01-02",
            "2024-01-03"
        );

        OffersDto expected = new OffersDto(List.of(createOffer(17000, 20000), createOffer(16000, 19000)));
        assertThat(Json.convertToString(result.getBody())).isEqualTo(jsonResultAttempt.write(expected).getJson());
    }

    private static OfferDto createOffer(int from, int to) {
        return new OfferDto(
            from,
            to,
            "PLN",
            "php",
            "Software Development Engineer",
            "remitly-software-development-engineer-krakow-go-5fbdbda0"
        );
    }
}
