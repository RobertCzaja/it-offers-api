package pl.api.itoffers.helper;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class WireMockOrchestrator {

  public static void pathWillReturn(String urlPath, String responseBodyFileName)
      throws IOException {
    WireMockServer wireMockServer = new WireMockServer(8080);
    wireMockServer.start();

    wireMockServer.stubFor(
        get(urlEqualTo(urlPath))
            .willReturn(
                aResponse().withStatus(200).withBody(FileManager.readFile(responseBodyFileName))));
  }
}
