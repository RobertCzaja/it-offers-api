package pl.api.itoffers.unit.provider.justjoinit.service.extractor.v2;

import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.PayloadFromJsonExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PayloadFromJsonExtractorTest {
    private FileManager fileManager = new FileManager();

    @Test
    public void testShouldCorrectlyExtractOffersFromJson() throws IOException {
        String jjitJson = this.fileManager.readFile(JustJoinItParams.ALL_LOCATIONS_PAYLOAD_B1_DECEMBER_PATH_JSON);

        ArrayList<Map<String, Object>> payload = new PayloadFromJsonExtractor().extractPayload(jjitJson);

        assertThat(payload).hasSize(100);
    }

    @Test
    public void testCannotExtractOffersFromJsonWhenJsonIsAnEmptyString() throws IOException {
        assertThrows(
            JustJoinItException.class,
            () -> new PayloadFromJsonExtractor().extractPayload("")
        );
        assertThrows(
            JustJoinItException.class,
            () -> new PayloadFromJsonExtractor().extractPayload(null)
        );
    }
}
