package pl.api.itoffers.shared.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonNodeMapper {

  private final ObjectMapper mapper = new ObjectMapper();

  public ArrayList<Map<String, Object>> mapToList(Iterator<JsonNode> jsonNodeIterator) {
    ArrayList<Map<String, Object>> offers = new ArrayList<>();
    while (jsonNodeIterator.hasNext()) {
      offers.add(this.mapToHash(jsonNodeIterator.next()));
    }
    return offers;
  }

  public Map<String, Object> mapToHash(JsonNode jsonNode) {
    return mapper.convertValue(jsonNode, new TypeReference<>() {});
  }

  public Map<String, Object> mapToHash(String json) throws JsonProcessingException {
    return mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});
  }
}
