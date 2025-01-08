package pl.api.itoffers.helper;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class WireMockOrchestrator {

  private static WireMockServer server;

  public static void pathWillReturn(String urlPath, String responseBodyFileName)
      throws IOException {

    if (null == server) {
      server = new WireMockServer(8080);
      server.start();
    }

    server.stubFor(
        get(urlEqualTo(urlPath))
            .willReturn(
                aResponse().withStatus(200).withBody(FileManager.readFile(responseBodyFileName))));
  }
}
