package pl.api.itoffers.unit.provider.nofluffjobs.fetcher.details;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.OffersDetailsFromJsonPayloadExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class OffersDetailsFromJsonPayloadExtractorTest {

  private OffersDetailsFromJsonPayloadExtractor extractor;

  @Before
  public void setUp() throws Exception {
    this.extractor = new OffersDetailsFromJsonPayloadExtractor();
  }

  @Test
  public void shouldCorrectlyExtractOffersFromRawPayload() throws IOException {
    String detailsJavaPayload = FileManager.readFile(NoFluffJobsParams.DETAILS_JAVA_JSON_PATH);

    Map<String, Object> extractedJsonOfferDetails = extractor.extractOffers(detailsJavaPayload);

    assertThat(extractedJsonOfferDetails)
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
        .hasSize(16);
  }

  // todo add missing tests
}
