package pl.api.itoffers.provider.justjoinit.service.extractor.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItPayloadExtractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PayloadFromJsonExtractor {

    private ObjectMapper mapper = new ObjectMapper();

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
            return convert(offersNode);
        } catch (JsonProcessingException e) {
            throw new JustJoinItException("Could not extract offers from raw JSON payload. " + e.getMessage());
        }
    }

    /**
     * Todo remove duplication from
     * @see JustJoinItPayloadExtractor
     */
    public ArrayList<Map<String, Object>> convert(Iterator<JsonNode> offersNode) {
        ArrayList<Map<String, Object>> offers = new ArrayList<>();
        while (offersNode.hasNext()) {
            offers.add(mapper.convertValue(offersNode.next(), new TypeReference<Map<String, Object>>() {}));
        }
        return offers;
    }
}
