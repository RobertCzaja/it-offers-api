package pl.api.itoffers.provider.justjoinit.service.extractor.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.provider.justjoinit.service.extractor.OffersPayloadMapper;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Service
public class JustJoinItPayloadExtractor {

    private final ObjectMapper mapper = new ObjectMapper();
    private final FileManager fileManager = new FileManager();
    private final OffersPayloadMapper offersPayloadMapper = new OffersPayloadMapper();

    public final ArrayList<Map<String, Object>> extract(String rawJsonPayload) {
        if (rawJsonPayload.isEmpty()) {
            throw new JustJoinItException("Empty body payload");
        }

        try {
            Iterator<JsonNode> offersNode = mapper.readTree(rawJsonPayload)
                    .path("props")
                    .path("pageProps")
                    .path("dehydratedState")
                    .path("queries")
                    .get(0)
                    .path("state")
                    .path("data")
                    .path("pages")
                    .get(0)
                    .path("data")
                    .elements();
            return offersPayloadMapper.convert(offersNode);
        } catch (JsonProcessingException e) {
            fileManager.saveFile(rawJsonPayload, "json");
            throw new JustJoinItException("Could not extract offers from raw JSON payload. " + e.getMessage());
        }
    }
}
