package pl.api.itoffers.unit.provider.nofluffjobs;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.junit.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.provider.nofluffjobs.PayloadFromJsonExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class PayloadFromJsonExtractorTest {

  @Test
  public void shouldCorrectlyExtractOffersFromRawPayload() throws IOException {

    String listPhpPayload = FileManager.readFile(NoFluffJobsParams.LIST_PHP_JSON_PATH);

    PayloadFromJsonExtractor extractor = new PayloadFromJsonExtractor();

    ArrayList<Map<String, Object>> extractedJsonOffers = extractor.extractOffers(listPhpPayload);

    assertThat(extractedJsonOffers).hasSize(20);
    // todo add custom assert class which will check also each element structure
  }
}
