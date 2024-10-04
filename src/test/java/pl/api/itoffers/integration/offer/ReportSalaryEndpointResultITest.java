package pl.api.itoffers.integration.offer;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.ReportAssert;
import pl.api.itoffers.integration.offer.helper.ReportSalariesEndpointCaller;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;


import static org.assertj.core.api.Assertions.assertThat;

public class ReportSalaryEndpointResultITest extends AbstractITest {

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

        HttpEntity<OffersDto> result = caller.makeRequest(20000);

        assertThat(result.getBody().getList()).hasSize(4);
        String serializedBody = new Gson().toJson(result.getBody());
        assertThat(serializedBody).isEqualTo("{\"list\":["+
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
}
