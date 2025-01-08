package pl.api.itoffers.unit.provider.nofluffjobs.fetcher.details;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.JsonFromOfferDetailsHtmlExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class JsonFromOfferDetailsHtmlExtractorTest {

  private final ObjectMapper mapper = new ObjectMapper();
  private final JsonFromOfferDetailsHtmlExtractor extractor =
      new JsonFromOfferDetailsHtmlExtractor();

  @Test
  void shouldExtractJsonPayloadFromNoFluffJobsOfferDetailsHtmlPage() throws IOException {
    String nfjHtml = FileManager.readFile(NoFluffJobsParams.DETAILS_JAVA_HTML_PATH);

    String extractedRawJsonPayload = extractor.getRawJsonFromHtml(Jsoup.parse(nfjHtml));

    assertThat(mapper.readTree(extractedRawJsonPayload)).isNotNull().isNotEmpty();
  }
}
