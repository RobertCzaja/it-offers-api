package pl.api.itoffers.unit.provider.nofluffjobs.fetcher.details;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.helper.assertions.NfjOfferDetailsAssert;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.OfferDetailsFromJsonPayloadExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class OfferDetailsFromJsonPayloadExtractorTest {

  private OfferDetailsFromJsonPayloadExtractor extractor;

  @Before
  public void setUp() throws Exception {
    this.extractor = new OfferDetailsFromJsonPayloadExtractor();
  }

  @Test
  public void shouldCorrectlyExtractOfferDetailsFromRawPayload() throws IOException {
    String detailsJavaPayload = FileManager.readFile(NoFluffJobsParams.DETAILS_JAVA_JSON_PATH);

    Map<String, Object> extractedJsonOfferDetails = extractor.extractOffer(detailsJavaPayload);

    NfjOfferDetailsAssert.expects(extractedJsonOfferDetails, 16);
  }

  @Test
  public void cannotExtractAnythingWhenRawPayloadIsEmpty() {
    assertThrows(NoFluffJobsException.class, () -> extractor.extractOffer(""));
    assertThrows(NoFluffJobsException.class, () -> extractor.extractOffer(null));
    assertThrows(NoFluffJobsException.class, () -> extractor.extractOffer("{}"));
    assertThrows(NoFluffJobsException.class, () -> extractor.extractOffer("{\"@graph\":[]}"));
    assertThrows(
        NoFluffJobsException.class,
        () -> extractor.extractOffer("{\"@graph\":[{\"@type\":\"unknown\"}]}"));
  }
}
