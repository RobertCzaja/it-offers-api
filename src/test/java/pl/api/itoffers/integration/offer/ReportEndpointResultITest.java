package pl.api.itoffers.integration.offer;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.helper.assertions.ExpectedCategories;
import pl.api.itoffers.helper.assertions.OfferCategoriesAssert;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.ReportCategoriesEndpointCaller;
import pl.api.itoffers.offer.application.dto.outgoing.CategoriesStatisticsDto;

public class ReportEndpointResultITest extends AbstractITest {
  @Autowired private OfferTestManager offerTestManager;
  @Autowired private ReportCategoriesEndpointCaller reportCategoriesEndpointCaller;
  private OfferBuilder builder;

  @BeforeEach
  @Override
  public void setUp() {
    super.setUp();
    this.builder = offerTestManager.createOfferBuilder();
    this.builder.notGenerateEntityIdsBecauseTheseShouldBeGeneratedByJPA();
    offerTestManager.clearAll();
  }

  @Test
  public void shouldFilterOffersByDatesAndTechnologies() {
    builder.job("php").at("08-30").skills("php", "docker").save();
    builder.job("php").at("09-01").skills("php", "mysql", "docker").save();
    builder.job("php").at("09-01").skills("php").save();
    builder.job("php").at("09-01").skills("php", "zabbix").save();
    builder.job("php").at("09-01").skills("php", "cypress").save();
    builder.job("php").at("09-02").skills("php", "docker").save();
    builder.job("php").at("09-03").skills("php", "docker", "kubernetes").save();
    builder.job("java").at("08-30").skills("java", "spring").save();
    builder.job("java").at("09-01").skills("java").save();
    builder.job("python").at("09-01").skills("python", "django").save();

    ResponseEntity<CategoriesStatisticsDto> response =
        reportCategoriesEndpointCaller.makeRequestForObject(
            "2024-09-01", "2024-09-02", List.of("php", "java"));

    OfferCategoriesAssert.hasAppliedFilters(response.getBody(), new String[] {"php", "java"});
    OfferCategoriesAssert.hasExactCategories(
        "php",
        response.getBody(),
        new ExpectedCategories()
            .add("php", 50.0, 5)
            .add("docker", 20.0, 2)
            .add("cypress", 10.0, 1)
            .add("mysql", 10.0, 1)
            .add("zabbix", 10.0, 1));
    OfferCategoriesAssert.hasTechnology("java", response.getBody());
    OfferCategoriesAssert.hasNotTechnology("python", response.getBody());
  }

  @Test
  public void whenTechnologiesAreNotProvidedReturnsNoResults() {
    builder.job("php").at("08-30").skills("php", "docker").save();

    ResponseEntity<CategoriesStatisticsDto> response =
        reportCategoriesEndpointCaller.makeRequestForObject(null, null, null);

    OfferCategoriesAssert.hasAppliedFilters(response.getBody(), null);
    OfferCategoriesAssert.hasNoResults(response.getBody());
  }
}
