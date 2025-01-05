package pl.api.itoffers.integration.provider.nofluffjobs;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.WireMockOrchestrator;
import pl.api.itoffers.provider.nofluffjobs.fetcher.NoFluffJobsParameters;
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.NoFluffJobsConnector;

public class NoFluffJobsConnectorITest extends AbstractITest {

  @Autowired private NoFluffJobsConnector connector;
  @Autowired private NoFluffJobsParameters parameters;
  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void shouldFetchRawJsonPayloadFromNoFluffJobsPage() throws IOException {
    String technology = "php";

    WireMockOrchestrator.pathWillReturn(
        parameters.listPath(technology), NoFluffJobsParams.LIST_PHP_HTML_PATH);

    String rawJson = connector.fetchStringifyJsonPayload(technology);

    assertThat(mapper.readTree(rawJson)).isNotNull();
  }
}
