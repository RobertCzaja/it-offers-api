package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import pl.api.itoffers.helper.AbstractITest;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.ApiAuthorizationHelper;
import pl.api.itoffers.helper.AuthorizationCredentials;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.offer.application.dto.outgoing.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;
import pl.api.itoffers.offer.ui.controller.ReportController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportSalaryEndpointResultITest extends AbstractITest {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ApiAuthorizationHelper apiAuthorizationHelper;
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
        // todo think about create more generic Builder method to create Offer that will take as a parameter only Salary
        // todo add some "usd" offers, that I can be sure is not in the final result
        // todo add single offer with two salaries: one that meats the requirements and the second which doesn't
        this.builder.job("php").skills("php").pln(15000, 18000).save();
        this.builder.job("php").skills("php").pln(17000, 21000).save();
        this.builder.job("php").skills("php").pln(18000, 23000).save();
        this.builder.job("java").skills("java").pln(17000, 19000).save();
        this.builder.job("java").skills("java").pln(18000, 24000).save();
        this.builder.job("java").skills("java").pln(21500, 26000).save();

        HttpEntity<OffersDto> result = template.exchange(
            ReportController.PATH_SALARIES,
            HttpMethod.GET,
            new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
            OffersDto.class
        );

        // todo add assertions
        assertThat(result.getBody().getList()).hasSize(4);
    }
}
