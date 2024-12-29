package pl.api.itoffers.helper.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Map;

public class JjitOffersAssert {
  public static void expectsSize(int expectedSize, ArrayList<Map<String, Object>> offers) {
    assertThat(offers).hasSize(expectedSize);
    offers.forEach(
        (serializedOffer) -> {
          assertThat(serializedOffer).containsKey("slug");
          assertThat(serializedOffer).containsKey("title");
          assertThat(serializedOffer).containsKey("requiredSkills");
          assertThat(serializedOffer).containsKey("niceToHaveSkills");
          assertThat(serializedOffer).containsKey("workplaceType");
          assertThat(serializedOffer).containsKey("workingTime");
          assertThat(serializedOffer).containsKey("experienceLevel");
          assertThat(serializedOffer).containsKey("employmentTypes");
          assertThat(serializedOffer).containsKey("categoryId");
          assertThat(serializedOffer).containsKey("multilocation");
          assertThat(serializedOffer).containsKey("city");
          assertThat(serializedOffer).containsKey("street");
          assertThat(serializedOffer).containsKey("latitude");
          assertThat(serializedOffer).containsKey("longitude");
          assertThat(serializedOffer).containsKey("remoteInterview");
          assertThat(serializedOffer).containsKey("companyName");
          assertThat(serializedOffer).containsKey("companyLogoThumbUrl");
          assertThat(serializedOffer).containsKey("publishedAt");
          assertThat(serializedOffer).containsKey("openToHireUkrainians");
        });
  }
}
