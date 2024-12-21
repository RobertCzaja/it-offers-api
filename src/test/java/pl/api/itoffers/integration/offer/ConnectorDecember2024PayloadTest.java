package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.integration.provider.justjoinit.payload.JustJoinItParams;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO name to change
 * todo move to Unit Tests directory as well with the example html
 */
public class ConnectorDecember2024PayloadTest {

    private FileManager fileManager;
    
    @BeforeEach
    public void setUp() {
        this.fileManager = new FileManager();
    }

    @Test
    void shouldParseJJITOffersHtmlIntroducedInDecember2024() throws IOException {

        String result = this.fileManager.readFile(JustJoinItParams.ALL_LOCATIONS_PAYLOAD_B1_DECEMBER);

        this.productionMethodImplementation(result);

        assertThat("").isEmpty();
    }

    private void productionMethodImplementation(String html) {

        String a = "";
    }
}
