package pl.api.itoffers.unit.provider.nofluffjobs;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.provider.nofluffjobs.JsonFromHtmlExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class JsonFromHtmlExtractorTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private final JsonFromHtmlExtractor extractor = new JsonFromHtmlExtractor();

  @Test
  void shouldExtractJsonPayloadFromNoFluffJobsHtmlPage() throws IOException {
    String nfjHtml = FileManager.readFile(NoFluffJobsParams.LIST_PHP_HTML_PATH);

    String extractedRawJsonPayload = extractor.getRawJsonFromHtml(Jsoup.parse(nfjHtml));

    assertThat(mapper.readTree(extractedRawJsonPayload)).isNotNull().isNotEmpty();
  }
}
