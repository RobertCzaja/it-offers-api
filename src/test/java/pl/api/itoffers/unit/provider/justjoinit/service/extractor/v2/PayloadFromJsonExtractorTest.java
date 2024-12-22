package pl.api.itoffers.unit.provider.justjoinit.service.extractor.v2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.provider.justjoinit.service.extractor.OffersPayloadMapper;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.PayloadFromJsonExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PayloadFromJsonExtractorTest {
    private FileManager fileManager = new FileManager();
    private PayloadFromJsonExtractor payloadFromJsonExtractor;

    @BeforeEach
    void setUp() {
        this.payloadFromJsonExtractor = new PayloadFromJsonExtractor(new OffersPayloadMapper());
    }

    @Test
    public void testShouldCorrectlyExtractOffersFromJson() throws IOException {
        String jjitJson = this.fileManager.readFile(JustJoinItParams.ALL_LOCATIONS_PAYLOAD_B1_DECEMBER_PATH_JSON);

        ArrayList<Map<String, Object>> offers = payloadFromJsonExtractor.extractPayload(jjitJson);

        assertThat(offers).hasSize(100);
        offers.forEach((serializedOffer) -> {
            assertThat(serializedOffer).containsKey("slug");
            assertThat(serializedOffer).containsKey("title");
            assertThat(serializedOffer).containsKey("requiredSkills");
            assertThat(serializedOffer).containsKey("niceToHaveSkills");
            assertThat(serializedOffer).containsKey("workplaceType");
            assertThat(serializedOffer).containsKey("workingTime");
            assertThat(serializedOffer).containsKey("experienceLevel");
            assertThat(serializedOffer).containsKey("employmentTypes");
            assertThat(serializedOffer).containsKey("categoryId");
            assertThat(serializedOffer).containsKey("multilocation");
            assertThat(serializedOffer).containsKey("city");
            assertThat(serializedOffer).containsKey("street");
            assertThat(serializedOffer).containsKey("latitude");
            assertThat(serializedOffer).containsKey("longitude");
            assertThat(serializedOffer).containsKey("remoteInterview");
            assertThat(serializedOffer).containsKey("companyName");
            assertThat(serializedOffer).containsKey("companyLogoThumbUrl");
            assertThat(serializedOffer).containsKey("publishedAt");
            assertThat(serializedOffer).containsKey("openToHireUkrainians");
        });
    }

    @Test
    public void testCannotExtractOffersFromJsonWhenJsonIsAnEmptyString() throws IOException {
        assertThrows(
            JustJoinItException.class,
            () -> payloadFromJsonExtractor.extractPayload("")
        );
        assertThrows(
            JustJoinItException.class,
            () -> payloadFromJsonExtractor.extractPayload(null)
        );
    }
}
