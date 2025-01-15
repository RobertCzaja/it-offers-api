package pl.api.itoffers.unit.provider.nofluffjobs.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.data.nfj.NoFluffJobsRawOfferModelsFactory;
import pl.api.itoffers.offer.domain.*;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory;

public class OfferFactoryTest {

  @Test
  void shouldMapRawNoFluffJobsModelsToDomainOfferModel()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A1_PHP);

    var offerMetadata =
        OfferFactory.createOfferMetadata(noFluffJobsModels.list(), noFluffJobsModels.details());
    var origin = OfferFactory.createOrigin(noFluffJobsModels.list());
    var salaries = OfferFactory.createSalaries(noFluffJobsModels.list());
    var categories = OfferFactory.createCategories(noFluffJobsModels.details());
    var company = OfferFactory.createCompany(noFluffJobsModels.list());

    assertOffer(origin, salaries, categories, company, offerMetadata, "remote");
  }

  @Test
  void shouldSetOfferAsAHybridTypeWhenItIsNotFullyRemote()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A2_PHP);

    var offerMetadata =
        OfferFactory.createOfferMetadata(noFluffJobsModels.list(), noFluffJobsModels.details());
    var origin = OfferFactory.createOrigin(noFluffJobsModels.list());
    var salaries = OfferFactory.createSalaries(noFluffJobsModels.list());
    var categories = OfferFactory.createCategories(noFluffJobsModels.details());
    var company = OfferFactory.createCompany(noFluffJobsModels.list());

    assertOffer(origin, salaries, categories, company, offerMetadata, "hybrid");
  }

  @Test
  void cannotBuildOfferModelWhenThereIsNoCompanyAddress()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A3_PHP);

    assertThrows(
        NoFluffJobsException.class,
        () -> {
          OfferFactory.createCompany(noFluffJobsModels.list());
        });
  }

  private static void assertOffer(
      Origin origin,
      Set<Salary> salaries,
      Set<Category> categories,
      Company company,
      OfferMetadata offerMetadata,
      String workplace) {
    assertThat(origin.getProvider()).isEqualTo(Origin.Provider.NO_FLUFF_JOBS);
    assertThat(categories).hasSize(17);
    assertThat(salaries).hasSize(1);
    var salary = salaries.stream().findFirst().get();
    assertThat(salary).isNotNull();
    assertThat(salary.getAmount().getCurrency()).isEqualTo("PLN");
    assertThat(salary.getAmount().getFrom()).isEqualTo(18000);
    assertThat(salary.getAmount().getTo()).isEqualTo(27000);
    assertThat(salary.getEmploymentType()).isEqualTo("b2b");
    assertThat(salary.getIsOriginal()).isTrue();
    assertThat(company.getName()).isEqualTo("CodiLime");
    assertThat(company.getCity()).isEqualTo("Warszawa");
    assertThat(company.getStreet()).isEqualTo("Grzybowska 5a");
    assertThat(offerMetadata.technology()).isEqualTo("php");
    assertThat(offerMetadata.slug())
        .isEqualTo("sr-network-security-architect-pre-sales-engineer-codilime-remote");
    assertThat(offerMetadata.title())
        .isEqualTo("Sr Network Security Architect / Pre-sales Engineer");
    assertThat(offerMetadata.seniority()).isEqualTo("senior");
    assertThat(offerMetadata.workplace()).isEqualTo(workplace);
    assertThat(offerMetadata.time()).isEqualTo("CONTRACTOR");
    assertThat(offerMetadata.remoteInterview()).isTrue();
    assertThat(offerMetadata.publishedAt())
        .isEqualTo(LocalDateTime.of(2025, 1, 10, 17, 27, 5, 231000000));
  }
}
