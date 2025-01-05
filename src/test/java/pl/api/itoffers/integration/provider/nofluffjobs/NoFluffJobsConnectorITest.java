package pl.api.itoffers.integration.provider.nofluffjobs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.provider.nofluffjobs.NoFluffJobsConnector;
import pl.api.itoffers.provider.nofluffjobs.NoFluffJobsParameters;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class NoFluffJobsConnectorITest extends AbstractITest {

  @Autowired private NoFluffJobsConnector connector;
  @Autowired private NoFluffJobsParameters parameters;

  @Test
  void shouldFetchRawJsonPayloadFromNoFluffJobsPage() throws IOException {

    // todo -------------------- put to some separated class --------------------
    WireMockServer wireMockServer = new WireMockServer(8080);
    wireMockServer.start();

    wireMockServer.stubFor(
        get(urlEqualTo(parameters.listPath("php")))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withBody(FileManager.readFile(NoFluffJobsParams.LIST_PHP_HTML_PATH))));
    // todo ----------------------------------------------------------------------

    String jsonPayload = connector.fetchStringifyJsonPayload("php");

    // todo add implementation
    assertThat("").isNotNull();
  }
}
