package pl.api.itoffers.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.integration.provider.justjoinit.inmemory.JustJoinItInMemoryConnector;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.service.extractor.v1.JustJoinItOffersFetcherV1;
import pl.api.itoffers.provider.justjoinit.service.extractor.v1.JustJoinItPayloadExtractor;

@Service
@RequiredArgsConstructor
public class JustJoinItProviderFactory {

  private final JustJoinItPayloadExtractor payloadExtractor;
  private final JustJoinItRepository repository;

  public JustJoinItProvider create() {
    return new JustJoinItProvider(
        new JustJoinItOffersFetcherV1(JustJoinItInMemoryConnector.create(), payloadExtractor),
        repository);
  }

  public JustJoinItProvider create(JustJoinItInMemoryConnector connector) {
    return new JustJoinItProvider(
        new JustJoinItOffersFetcherV1(connector, payloadExtractor), repository);
  }
}
