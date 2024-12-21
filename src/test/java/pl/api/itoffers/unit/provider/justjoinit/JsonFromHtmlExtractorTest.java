package pl.api.itoffers.unit.provider.justjoinit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.JsonFromHtmlExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonFromHtmlExtractorTest {
    private FileManager fileManager = new FileManager();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldParseJJITOffersHtmlIntroducedInDecember2024() throws IOException {

        String jjitHtml = this.fileManager.readFile(JustJoinItParams.ALL_LOCATIONS_PAYLOAD_B1_DECEMBER);

        String rawJson = new JsonFromHtmlExtractor().getRawJsonFromHtml(jjitHtml);

        assertThat(mapper.readTree(rawJson)).isNotNull();
    }
}
