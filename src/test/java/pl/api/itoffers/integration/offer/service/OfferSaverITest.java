package pl.api.itoffers.integration.offer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.data.nfj.NoFluffJobsRawOfferModelsFactory;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory;

public class OfferSaverITest extends AbstractITest {

  @Autowired private OfferSaver offerSaver;
  @Autowired private OfferTestManager offerTestManager;
  private OfferBuilder builder;

  @BeforeEach
  public void setUp() {
    super.setUp();
    this.builder = offerTestManager.createOfferBuilder();
    this.builder.notGenerateEntityIdsBecauseTheseShouldBeGeneratedByJPA();
    offerTestManager.clearAll();
  }

  @Test
  public void shouldCorrectlySaveOffers()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A1_PHP);

    var categories = OfferFactory.createCategories(noFluffJobsModels.details());
    var salaries = OfferFactory.createSalaries(noFluffJobsModels.list());
    var company = OfferFactory.createCompany(noFluffJobsModels.list());
    offerSaver.save(categories, salaries, company);

    // todo under the development

    assertThat("").isNotNull(); // todo add real assertions
  }
}
