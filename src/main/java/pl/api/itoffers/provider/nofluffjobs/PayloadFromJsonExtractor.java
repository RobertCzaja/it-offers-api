package pl.api.itoffers.provider.nofluffjobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import pl.api.itoffers.shared.utils.json.JsonNodeMapper;

/** TODO name of the class to figure out */
public class PayloadFromJsonExtractor {

  private final ObjectMapper mapper = new ObjectMapper();
  private final JsonNodeMapper jsonNodeMapper = new JsonNodeMapper();

  /** TODO add custom exception */
  public ArrayList<Map<String, Object>> extractOffers(String rawJsonPayload) {

    if (null == rawJsonPayload || rawJsonPayload.isEmpty()) {
      throw new RuntimeException("Empty body payload"); // todo test that
    }

    try {
      JsonNode node = mapper.readTree(rawJsonPayload);

      Iterator<JsonNode> list =
          node.get("STORE_KEY").get("searchResponse").get("postings").elements();

      return jsonNodeMapper.map(list);
    } catch (JsonProcessingException e) {
      // todo log whole raw payload?
      throw new RuntimeException(e);
    }
  }
}
