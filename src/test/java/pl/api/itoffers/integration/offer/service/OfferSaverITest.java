package pl.api.itoffers.integration.offer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.data.nfj.NoFluffJobsRawOfferModelsFactory;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.OffersAssert;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory;

public class OfferSaverITest extends AbstractITest {

  @Autowired private OfferSaver offerSaver;
  @Autowired private OfferTestManager offerTestManager;
  @Autowired private OfferRepository offerRepository;
  @Autowired private CategoryRepository categoryRepository;
  @Autowired private CompanyRepository companyRepository;
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
    var origin = OfferFactory.createOrigin(noFluffJobsModels.list());
    var offerMetadata =
        OfferFactory.createOfferMetadata(noFluffJobsModels.list(), noFluffJobsModels.details());
    var categories = OfferFactory.createCategories(noFluffJobsModels.details());
    var salaries = OfferFactory.createSalaries(noFluffJobsModels.list());
    var company = OfferFactory.createCompany(noFluffJobsModels.list());

    offerSaver.save(origin, offerMetadata, categories, salaries, company);

    var offers = offerRepository.findAll();
    assertThat(offers).hasSize(1);
    assertThat(categoryRepository.findAll()).hasSize(17);
    assertThat(companyRepository.findAll()).hasSize(1);

    OffersAssert.hasExpectedOfferModel(
        offers.get(0),
        "php",
        "Sr Network Security Architect / Pre-sales Engineer",
        "sr-network-security-architect-pre-sales-engineer-codilime-remote",
        "CodiLime",
        17,
        LocalDateTime.of(2025, 1, 10, 17, 27, 5, 231000000),
        new OffersAssert.ExpectedSalary("b2b", "PLN", 18000, 27000, true));
  }

  @Test
  public void shouldDetectDuplications()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A1_PHP);
    var origin = OfferFactory.createOrigin(noFluffJobsModels.list());
    var offerMetadata =
        OfferFactory.createOfferMetadata(noFluffJobsModels.list(), noFluffJobsModels.details());
    var categories = OfferFactory.createCategories(noFluffJobsModels.details());
    var salaries = OfferFactory.createSalaries(noFluffJobsModels.list());
    var company = OfferFactory.createCompany(noFluffJobsModels.list());

    offerSaver.save(origin, offerMetadata, categories, salaries, company);
    offerSaver.save(origin, offerMetadata, categories, salaries, company);

    var offers = offerRepository.findAll();
    assertThat(offers).hasSize(1);
    assertThat(categoryRepository.findAll()).hasSize(17);
    assertThat(companyRepository.findAll()).hasSize(1);
  }
}
