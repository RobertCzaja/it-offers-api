package pl.api.itoffers.provider.nofluffjobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.shared.utils.json.JsonNodeMapper;

public class PayloadFromJsonExtractor {

  private final ObjectMapper mapper = new ObjectMapper();
  private final JsonNodeMapper jsonNodeMapper = new JsonNodeMapper();

  public ArrayList<Map<String, Object>> extractOffers(String rawJsonPayload) {

    if (null == rawJsonPayload || rawJsonPayload.isEmpty()) {
      throw NoFluffJobsException.payloadIsEmpty();
    }

    try {
      JsonNode node = mapper.readTree(rawJsonPayload);

      Iterator<JsonNode> list =
          node.get("STORE_KEY").get("searchResponse").get("postings").elements();

      return jsonNodeMapper.map(list);
    } catch (JsonProcessingException | NullPointerException e) {
      throw NoFluffJobsException.onExtractingOffers(rawJsonPayload, e);
    }
  }
}
