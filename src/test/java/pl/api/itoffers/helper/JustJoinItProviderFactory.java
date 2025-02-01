package pl.api.itoffers.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.integration.provider.justjoinit.inmemory.JustJoinItInMemoryConnector;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.service.v1.JustJoinItOffersFetcherV1;
import pl.api.itoffers.provider.justjoinit.service.v1.JustJoinItPayloadExtractor;
import pl.api.itoffers.report.service.ImportStatistics;

@Service
@RequiredArgsConstructor
public class JustJoinItProviderFactory {

  private final JustJoinItPayloadExtractor payloadExtractor;
  private final JustJoinItRepository repository;
  private final ImportStatistics importStatistics;

  public JustJoinItProvider create() {
    return new JustJoinItProvider(
        new JustJoinItOffersFetcherV1(JustJoinItInMemoryConnector.create(), payloadExtractor),
        repository,
        importStatistics);
  }

  public JustJoinItProvider create(JustJoinItInMemoryConnector connector) {
    return new JustJoinItProvider(
        new JustJoinItOffersFetcherV1(connector, payloadExtractor), repository, importStatistics);
  }
}
