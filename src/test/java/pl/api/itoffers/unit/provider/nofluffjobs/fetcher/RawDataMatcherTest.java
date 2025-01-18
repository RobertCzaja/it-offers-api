package pl.api.itoffers.unit.provider.nofluffjobs.fetcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.data.nfj.NoFluffJobsRawOfferModelsFactory;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.fetcher.RawDataMatcher;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;

public class RawDataMatcherTest {

  private static final String ID_1 = UUID.randomUUID().toString();
  private static final String ID_2 = UUID.randomUUID().toString();
  private static final String ID_3 = UUID.randomUUID().toString();

  @Test
  void couldNotMatchWhenTwoRawDatacollectionHaveDifferentSizes() throws IOException {
    var noFluffJobsModels = NoFluffJobsRawOfferModelsFactory.create(NoFluffJobsParams.A1_PHP);

    assertThrows(
        NoFluffJobsException.class,
        () -> RawDataMatcher.match(List.of(noFluffJobsModels.list()), List.of()));
  }

  @Test
  void shouldMatchEveryRecord() {
    List<NoFluffJobsRawListOffer> listOfferCollection =
        new ArrayList<>(
            List.of(
                NoFluffJobsRawOfferModelsFactory.createOfferList(UUID.fromString(ID_1)),
                NoFluffJobsRawOfferModelsFactory.createOfferList(UUID.fromString(ID_2)),
                NoFluffJobsRawOfferModelsFactory.createOfferList(UUID.fromString(ID_3))));

    List<NoFluffJobsRawDetailsOffer> detailsOfferCollection =
        new ArrayList<>(
            List.of(
                NoFluffJobsRawOfferModelsFactory.createDetailsList(UUID.fromString(ID_3)),
                NoFluffJobsRawOfferModelsFactory.createDetailsList(UUID.fromString(ID_1)),
                NoFluffJobsRawOfferModelsFactory.createDetailsList(UUID.fromString(ID_2))));

    var result = RawDataMatcher.match(listOfferCollection, detailsOfferCollection);

    assertThat(result).hasSize(3);
  }

  @Test
  void cannotMatchEmptyOfferList() {
    assertThrows(NoFluffJobsException.class, () -> RawDataMatcher.match(List.of(), List.of()));
  }

  @Test
  void cannotMatchWhenOneElementIsMissing() {
    List<NoFluffJobsRawListOffer> listOfferCollection =
        new ArrayList<>(
            List.of(
                NoFluffJobsRawOfferModelsFactory.createOfferList(UUID.fromString(ID_1)),
                NoFluffJobsRawOfferModelsFactory.createOfferList(UUID.fromString(ID_2)),
                NoFluffJobsRawOfferModelsFactory.createOfferList(UUID.fromString(ID_3))));

    List<NoFluffJobsRawDetailsOffer> detailsOfferCollection =
        new ArrayList<>(
            List.of(
                NoFluffJobsRawOfferModelsFactory.createDetailsList(UUID.fromString(ID_3)),
                NoFluffJobsRawOfferModelsFactory.createDetailsList(UUID.fromString(ID_1))
                // there is no details offer with ID_2
                ));

    assertThrows(
        NoFluffJobsException.class,
        () -> RawDataMatcher.match(listOfferCollection, detailsOfferCollection));
  }
}
