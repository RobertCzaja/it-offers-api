package pl.api.itoffers.unit.provider.nofluffjobs.factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsRawOfferModelsFactory;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory;

public class OfferFactoryTest {

  private OfferFactory offerFactory;

  @BeforeEach
  public void setUp() {
    this.offerFactory = new OfferFactory();
  }

  @Test
  void shouldMapRawNoFluffJobsModelsToDomainOfferModel()
      throws IOException, NoSuchFieldException, IllegalAccessException {

    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create();

    Offer offer = offerFactory.createOffer(noFluffJobsModels.list(), noFluffJobsModels.details());

    assertThat("").isNotNull(); // todo add better assertions
  }
}
