package pl.api.itoffers.shared.utils.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class JsonNodeMapper {

  private final ObjectMapper mapper = new ObjectMapper();

  public ArrayList<Map<String, Object>> mapToList(Iterator<JsonNode> jsonNodeIterator) {
    ArrayList<Map<String, Object>> offers = new ArrayList<>();
    while (jsonNodeIterator.hasNext()) {
      offers.add(mapper.convertValue(jsonNodeIterator.next(), new TypeReference<>() {}));
    }
    return offers;
  }
}
