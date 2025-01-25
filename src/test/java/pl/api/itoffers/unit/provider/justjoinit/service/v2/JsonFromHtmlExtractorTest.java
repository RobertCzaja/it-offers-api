package pl.api.itoffers.unit.provider.justjoinit.service.v2;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.provider.justjoinit.service.v2.JsonFromHtmlExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class JsonFromHtmlExtractorTest {
  private ObjectMapper mapper = new ObjectMapper();

  @Test
  void shouldParseJJITOffersHtmlIntroducedInDecember2024() throws IOException {

    String jjitHtml =
        FileManager.readFile(JustJoinItParams.V2_ALL_LOCATIONS_PAYLOAD_B1_DECEMBER_PATH_HTML);

    String rawJson = new JsonFromHtmlExtractor().getRawJsonFromHtml(Jsoup.parse(jjitHtml));

    assertThat(mapper.readTree(rawJson)).isNotNull();
  }
}
