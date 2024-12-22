package pl.api.itoffers.provider.justjoinit.service.extractor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.service.extractor.v1.JustJoinItPayloadExtractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Service
public class OffersPayloadMapper {

    private ObjectMapper mapper = new ObjectMapper();

    public ArrayList<Map<String, Object>> convert(Iterator<JsonNode> offersNode) {
        ArrayList<Map<String, Object>> offers = new ArrayList<>();
        while (offersNode.hasNext()) {
            offers.add(mapper.convertValue(offersNode.next(), new TypeReference<Map<String, Object>>() {}));
        }
        return offers;
    }
}
