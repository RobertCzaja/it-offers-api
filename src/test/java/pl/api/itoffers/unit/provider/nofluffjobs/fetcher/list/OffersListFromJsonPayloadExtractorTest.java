package pl.api.itoffers.unit.provider.nofluffjobs.fetcher.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.helper.assertions.NfjOffersAssert;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.OffersListFromJsonPayloadExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class OffersListFromJsonPayloadExtractorTest {

  private OffersListFromJsonPayloadExtractor extractor;

  @Before
  public void setUp() throws Exception {
    this.extractor = new OffersListFromJsonPayloadExtractor();
  }

  @Test
  public void shouldCorrectlyExtractOffersFromRawPayload() throws IOException {

    String listPhpPayload = FileManager.readFile(NoFluffJobsParams.LIST_PHP_JSON_PATH);

    ArrayList<Map<String, Object>> extractedJsonOffers = extractor.extractOffers(listPhpPayload);

    NfjOffersAssert.expects(extractedJsonOffers, 20);
  }

  @Test
  public void cannotExtractAnythingWhenRawPayloadIsEmpty() {
    assertThrows(NoFluffJobsException.class, () -> extractor.extractOffers(""));
    assertThrows(NoFluffJobsException.class, () -> extractor.extractOffers(null));
    assertThrows(NoFluffJobsException.class, () -> extractor.extractOffers("{}"));
    assertThrows(NoFluffJobsException.class, () -> extractor.extractOffers("\"STORE_KEY\":{}"));
  }
}
