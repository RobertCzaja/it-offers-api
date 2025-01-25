package pl.api.itoffers.integration.provider.justjoinit.inmemory;

import java.io.IOException;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItConnector;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class JustJoinItInMemoryConnector implements JustJoinItConnector {

  public String payloadPath = JustJoinItParams.ALL_LOCATIONS_PAYLOAD_PATH;

  public static JustJoinItInMemoryConnector create() {
    return new JustJoinItInMemoryConnector();
  }

  @Override
  public String fetchStringifyJsonPayload(String technology) {
    try {
      return FileManager.readFile(payloadPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
