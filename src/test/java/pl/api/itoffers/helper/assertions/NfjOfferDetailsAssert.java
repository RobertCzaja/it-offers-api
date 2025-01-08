package pl.api.itoffers.helper.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

public class NfjOfferDetailsAssert {

  public static void expects(Map<String, Object> nflOfferDetails, int expectedSize) {
    assertThat(nflOfferDetails)
        .hasFieldOrProperty("title")
        .hasFieldOrProperty("description")
        .hasFieldOrProperty("industry")
        .hasFieldOrProperty("occupationalCategory")
        .hasFieldOrProperty("jobImmediateStart")
        .hasFieldOrProperty("datePosted")
        .hasFieldOrProperty("validThrough")
        .hasFieldOrProperty("employmentType")
        .hasFieldOrProperty("directApply")
        .hasFieldOrProperty("hiringOrganization")
        .hasFieldOrProperty("experienceRequirements")
        .hasFieldOrProperty("jobLocationType")
        .hasFieldOrProperty("jobBenefits")
        .hasFieldOrProperty("baseSalary")
        .hasFieldOrProperty("skills")
        .hasSize(expectedSize);
  }
}
