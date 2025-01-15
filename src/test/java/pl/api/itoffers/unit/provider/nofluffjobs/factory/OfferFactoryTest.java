package pl.api.itoffers.unit.provider.nofluffjobs.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.data.nfj.NoFluffJobsRawOfferModelsFactory;
import pl.api.itoffers.helper.FrozenClock;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.OfferMetadata;
import pl.api.itoffers.offer.domain.Origin;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory;

public class OfferFactoryTest {

  private OfferFactory offerFactory;

  @BeforeEach
  public void setUp() {
    this.offerFactory = new OfferFactory(new FrozenClock());
  }

  @Test
  void shouldMapRawNoFluffJobsModelsToDomainOfferModel()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A1_PHP);

    var offer =
        offerFactory.createOffer(
            noFluffJobsModels.list(),
            noFluffJobsModels.details(),
            OfferFactory.createSalaries(noFluffJobsModels.list()),
            OfferFactory.createCategories(noFluffJobsModels.details()),
            OfferFactory.createCompany(noFluffJobsModels.list()));

    var offerMetadata =
        offerFactory.createOfferMetadata(noFluffJobsModels.list(), noFluffJobsModels.details());
    var origin = offerFactory.createOrigin(noFluffJobsModels.list());

    assertOrigin(origin);
    assertExpectedOfferMetadata(offerMetadata, "remote");
    assertExpectedOffer(offer, "remote");
  }

  @Test
  void shouldSetOfferAsAHybridTypeWhenItIsNotFullyRemote()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A2_PHP);

    var offer =
        offerFactory.createOffer(
            noFluffJobsModels.list(),
            noFluffJobsModels.details(),
            OfferFactory.createSalaries(noFluffJobsModels.list()),
            OfferFactory.createCategories(noFluffJobsModels.details()),
            OfferFactory.createCompany(noFluffJobsModels.list()));
    var offerMetadata =
        offerFactory.createOfferMetadata(noFluffJobsModels.list(), noFluffJobsModels.details());
    var origin = offerFactory.createOrigin(noFluffJobsModels.list());

    assertOrigin(origin);
    assertExpectedOfferMetadata(offerMetadata, "hybrid");
    assertExpectedOffer(offer, "hybrid");
  }

  @Test
  void cannotBuildOfferModelWhenThereIsNoCompanyAddress()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A3_PHP);

    assertThrows(
        NoFluffJobsException.class,
        () -> {
          offerFactory.createOffer(
              noFluffJobsModels.list(),
              noFluffJobsModels.details(),
              OfferFactory.createSalaries(noFluffJobsModels.list()),
              OfferFactory.createCategories(noFluffJobsModels.details()),
              OfferFactory.createCompany(noFluffJobsModels.list()));
        });
  }

  private static void assertOrigin(Origin origin) {
    assertThat(origin.getProvider()).isEqualTo(Origin.Provider.NO_FLUFF_JOBS);
  }

  /** todo move to separated asser class */
  private static void assertExpectedOfferMetadata(OfferMetadata offerMetadata, String workplace) {
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

  private static void assertExpectedOffer(Offer offer, String workplace) {
    assertThat(offer.getTechnology()).isEqualTo("php");
    assertThat(offer.getSlug())
        .isEqualTo("sr-network-security-architect-pre-sales-engineer-codilime-remote");
    assertThat(offer.getTitle()).isEqualTo("Sr Network Security Architect / Pre-sales Engineer");
    assertThat(offer.getSeniority()).isEqualTo("senior");
    assertThat(offer.getCharacteristics().getWorkplace()).isEqualTo(workplace);
    assertThat(offer.getCharacteristics().getTime()).isEqualTo("CONTRACTOR");
    assertThat(offer.getCharacteristics().getRemoteInterview()).isTrue();
    assertThat(offer.getCategories()).hasSize(17);
    assertThat(offer.getSalaries()).hasSize(1);
    var salary = offer.getSalaries().stream().findFirst().get();
    assertThat(salary).isNotNull();
    assertThat(salary.getAmount().getCurrency()).isEqualTo("PLN");
    assertThat(salary.getAmount().getFrom()).isEqualTo(18000);
    assertThat(salary.getAmount().getTo()).isEqualTo(27000);
    assertThat(salary.getEmploymentType()).isEqualTo("b2b");
    assertThat(salary.getIsOriginal()).isTrue();
    assertThat(offer.getPublishedAt())
        .isEqualTo(LocalDateTime.of(2025, 1, 10, 17, 27, 5, 231000000));
    assertThat(offer.getCreatedAt()).isEqualTo(LocalDateTime.of(2025, 1, 10, 17, 28, 5));
  }
}
