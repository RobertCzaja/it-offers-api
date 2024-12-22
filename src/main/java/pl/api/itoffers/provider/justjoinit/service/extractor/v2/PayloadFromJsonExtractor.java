package pl.api.itoffers.provider.justjoinit.service.extractor.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.provider.justjoinit.service.extractor.OffersPayloadMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Service
public class PayloadFromJsonExtractor {

    private final ObjectMapper mapper = new ObjectMapper();
    private final OffersPayloadMapper payloadMapper = new OffersPayloadMapper();

    public ArrayList<Map<String, Object>> extractPayload(String rawJsonPayload) {
        if (null == rawJsonPayload || rawJsonPayload.isEmpty()) {
            throw new JustJoinItException("Empty body payload");
        }

        try {
            Iterator<JsonNode> offersNode = mapper
                .readTree(rawJsonPayload)
                .path("state")
                .path("queries")
                .get(0)
                .path("state")
                .path("data")
                .path("pages")
                .get(0)
                .path("data")
                .elements();
            return payloadMapper.convert(offersNode);
        } catch (JsonProcessingException e) {
            throw new JustJoinItException("Could not extract offers from raw JSON payload. " + e.getMessage());
        }
    }
}
