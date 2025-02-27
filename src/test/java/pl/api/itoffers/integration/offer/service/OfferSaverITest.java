package pl.api.itoffers.integration.offer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.data.nfj.NoFluffJobsRawOfferModelsFactory;
import pl.api.itoffers.helper.*;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.OffersAssert;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.OfferDraft;
import pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory;
import pl.api.itoffers.report.service.ImportStatistics;

public class OfferSaverITest extends AbstractITest {

  @Autowired private OfferSaver offerSaver;
  @Autowired private OfferTestManager offerTestManager;
  @Autowired private OfferRepository offerRepository;
  @Autowired private CategoryRepository categoryRepository;
  @Autowired private CompanyRepository companyRepository;
  @Autowired private ImportStatistics importStatistics;
  private ImportStatisticsInitializer importStatisticsInitializer;
  private OfferBuilder builder;

  @BeforeEach
  @Override
  public void setUp() {
    super.setUp();
    this.builder = offerTestManager.createOfferBuilder();
    this.builder.notGenerateEntityIdsBecauseTheseShouldBeGeneratedByJPA();
    this.importStatisticsInitializer = new ImportStatisticsInitializer(importStatistics);
    offerTestManager.clearAll();
  }

  @AfterEach
  public void afterEach() {
    this.importStatisticsInitializer.finish(NoFluffJobsRawOfferModelsFactory.SCRAPING_ID);
  }

  @Test
  public void shouldCorrectlySaveOffers() throws IOException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A1_PHP);
    var origin = OfferFactory.createOrigin(noFluffJobsModels.list());
    var offerMetadata =
        OfferFactory.createOfferMetadata(noFluffJobsModels.list(), noFluffJobsModels.details());
    var categories = OfferFactory.createCategories(noFluffJobsModels.details());
    var salaries = OfferFactory.createSalaries(noFluffJobsModels.list());
    var company = OfferFactory.createCompany(noFluffJobsModels.list());
    var offerDraft = new OfferDraft(origin, offerMetadata, categories, salaries, company);
    importStatisticsInitializer.initialize(offerDraft);

    offerSaver.save(offerDraft);

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
  public void shouldDetectDuplications() throws IOException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A1_PHP);
    var origin = OfferFactory.createOrigin(noFluffJobsModels.list());
    var offerMetadata =
        OfferFactory.createOfferMetadata(noFluffJobsModels.list(), noFluffJobsModels.details());
    var categories = OfferFactory.createCategories(noFluffJobsModels.details());
    var salaries = OfferFactory.createSalaries(noFluffJobsModels.list());
    var company = OfferFactory.createCompany(noFluffJobsModels.list());
    var draft = new OfferDraft(origin, offerMetadata, categories, salaries, company);
    importStatisticsInitializer.initialize(draft);

    offerSaver.save(draft);
    offerSaver.save(draft);

    var offers = offerRepository.findAll();
    assertThat(offers).hasSize(1);
    assertThat(categoryRepository.findAll()).hasSize(17);
    assertThat(companyRepository.findAll()).hasSize(1);
  }

  @Test
  public void shouldNotSaveDuplicatedCompany() throws IOException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A1_PHP);
    var categories = OfferFactory.createCategories(noFluffJobsModels.details());
    var offerDraft =
        new OfferDraft(
            OfferSubObjectsFactory.createOrigin("100001"),
            OfferMetadataFactory.create("title1"),
            categories,
            OfferSubObjectsFactory.createSalaries(),
            OfferSubObjectsFactory.createCompany());
    importStatisticsInitializer.initialize(offerDraft);

    offerSaver.save(offerDraft);
    offerSaver.save(
        new OfferDraft(
            OfferSubObjectsFactory.createOrigin("100002"),
            OfferMetadataFactory.create("title2"),
            categories,
            OfferSubObjectsFactory.createSalaries(),
            OfferSubObjectsFactory.createCompany()));
    offerSaver.save(
        new OfferDraft(
            OfferSubObjectsFactory.createOrigin("100003"),
            OfferMetadataFactory.create("title3"),
            categories,
            OfferSubObjectsFactory.createSalaries(),
            OfferSubObjectsFactory.createCompany()));

    assertThat(companyRepository.findAll()).hasSize(1);
  }

  @Test
  public void shouldSaveCompanyWithNoAddress() throws IOException {
    var offerDraft =
        new OfferDraft(
            OfferSubObjectsFactory.createOrigin("100001"),
            OfferMetadataFactory.create("title1"),
            OfferFactory.createCategories(
                NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A1_PHP).details()),
            OfferSubObjectsFactory.createSalaries(),
            Company.createWithNoAddress("a"));
    importStatisticsInitializer.initialize(offerDraft);

    offerSaver.save(offerDraft);

    var offer = offerRepository.findAll().get(0);
    assertThat(offer.getCompany().getName()).isEqualTo("a");
    assertThat(offer.getCompany().getCity()).isNull();
    assertThat(offer.getCompany().getStreet()).isNull();
  }
}
