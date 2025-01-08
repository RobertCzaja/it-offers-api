package pl.api.itoffers.integration.provider.nofluffjobs;

import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.data.nfj.NoFluffJobsParams;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.WireMockOrchestrator;
import pl.api.itoffers.helper.assertions.NfjOfferDetailsAssert;
import pl.api.itoffers.provider.nofluffjobs.fetcher.NoFluffJobsParameters;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.NoFluffJobsDetailsFetcher;

public class NoFluffJobsDetailsFetcherITest extends AbstractITest {

  @Autowired private NoFluffJobsParameters parameters;
  @Autowired private NoFluffJobsDetailsFetcher fetcher;

  @Test
  void shouldFetchOfferDetailsFromNoFluffJobs() throws IOException {

    String slug = "senior-java-developer-boldare-west-pomeranian-1";
    WireMockOrchestrator.pathWillReturn(
        parameters.detailsPath(slug), NoFluffJobsParams.DETAILS_JAVA_HTML_PATH);

    Map<String, Object> offerDetails = fetcher.fetch(slug);

    NfjOfferDetailsAssert.expects(offerDetails, 16);
  }
}
