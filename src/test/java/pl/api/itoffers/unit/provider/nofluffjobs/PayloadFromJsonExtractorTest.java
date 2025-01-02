package pl.api.itoffers.unit.provider.nofluffjobs;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.provider.nofluffjobs.PayloadFromJsonExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class PayloadFromJsonExtractorTest {
  @Test
  public void _______() throws IOException {

    String listPhpPayload = FileManager.readFile(NoFluffJobsParams.LIST_PHP_JSON_PATH);

    PayloadFromJsonExtractor extractor = new PayloadFromJsonExtractor();

    String extractedJsonOffers = extractor.extractOffers(listPhpPayload);

    assertThat("").isNotNull();
  }
}
