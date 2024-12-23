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
            Iterator<JsonNode> queriesNodes = mapper
                .readTree(rawJsonPayload)
                .path("state")
                .path("queries")
                .elements();

            do {
                JsonNode queryNode = queriesNodes.next();
                if (queryNode.get("state").isObject()) {
                    Iterator<JsonNode> offerNodes = queryNode
                        .get("state")
                        .path("data")
                        .path("pages")
                        .get(0)
                        .path("data")
                        .elements();
                    return payloadMapper.convert(offerNodes);
                }
            } while (queriesNodes.hasNext());

            throw new JustJoinItException("Could not find offers node in raw JSON payload.");
        } catch (JsonProcessingException e) {
            throw new JustJoinItException("Could not extract offers from raw JSON payload. " + e.getMessage());
        }
    }
}
