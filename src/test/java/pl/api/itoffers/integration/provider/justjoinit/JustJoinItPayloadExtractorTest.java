package pl.api.itoffers.integration.provider.justjoinit;

import jakarta.validation.Payload;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import pl.api.itoffers.integration.provider.justjoinit.payload.JustJoinItParams;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItPayloadExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JustJoinItPayloadExtractorTest {

    @Test
    public void shouldExtractOffersFromValidPayload() throws IOException {

        String justJoinItPayload = FileManager.readFile(JustJoinItParams.ALL_LOCATIONS_PAYLOAD_PATH);

        ArrayList<Map<String, Object>> offers =  new JustJoinItPayloadExtractor().extract(justJoinItPayload);

        assertThat(offers).hasSize(87);
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

}
