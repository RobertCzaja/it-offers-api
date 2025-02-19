package pl.api.itoffers.helper.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

public class NfjOffersAssert {

  public static void expects(List<Map<String, Object>> nfjOffers, int expectedSize) {
    assertThat(nfjOffers).hasSize(expectedSize);
    nfjOffers.forEach(
        (serializedOffer) -> {
          assertThat(serializedOffer).containsKey("id");
          assertThat(serializedOffer).containsKey("name");
          assertThat(serializedOffer).containsKey("location");
          assertThat(serializedOffer).containsKey("posted");
          if (null != serializedOffer.get("renewed")) {
            assertThat(serializedOffer).containsKey("renewed");
            assertThat(serializedOffer.get("renewed")).isNotNull();
          }
          assertThat(serializedOffer).containsKey("title");
          assertThat(serializedOffer).containsKey("technology");
          assertThat(serializedOffer).containsKey("logo");
          assertThat(serializedOffer).containsKey("category");
          assertThat(serializedOffer).containsKey("seniority");
          assertThat(serializedOffer).containsKey("url");
          assertThat(serializedOffer).containsKey("regions");
          assertThat(serializedOffer).containsKey("fullyRemote");
          assertThat(serializedOffer).containsKey("salary");
          assertThat(serializedOffer).containsKey("flavors");
          assertThat(serializedOffer).containsKey("topInSearch");
          assertThat(serializedOffer).containsKey("highlighted");
          assertThat(serializedOffer).containsKey("help4Ua");
          assertThat(serializedOffer).containsKey("reference");
          assertThat(serializedOffer).containsKey("searchBoost");
          assertThat(serializedOffer).containsKey("onlineInterviewAvailable");
          assertThat(serializedOffer).containsKey("tiles");
        });
  }
}
