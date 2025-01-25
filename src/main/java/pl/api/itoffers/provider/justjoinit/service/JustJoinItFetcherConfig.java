package pl.api.itoffers.provider.justjoinit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.api.itoffers.provider.justjoinit.service.v1.JustJoinItOffersFetcherV1;
import pl.api.itoffers.provider.justjoinit.service.v2.JustJoinItHttpConnectorV2;
import pl.api.itoffers.provider.justjoinit.service.v2.JustJoinItOffersFetcherV2;
import pl.api.itoffers.provider.justjoinit.service.v2.PayloadFromJsonExtractor;

@Configuration
@RequiredArgsConstructor
public class JustJoinItFetcherConfig {
  private final JustJoinItOffersFetcherV1 justJoinItOffersFetcherV1;
  private final JustJoinItHttpConnectorV2 justJoinItHttpConnectorV2;
  private final PayloadFromJsonExtractor extractor;

  /**
   * @deprecated since december 2024
   */
  @Bean
  public JustJoinItOffersFetcherV1 v1() {
    return justJoinItOffersFetcherV1;
  }

  @Bean
  @Primary
  public JustJoinItOffersFetcherV2 v2() {
    return new JustJoinItOffersFetcherV2(justJoinItHttpConnectorV2, extractor);
  }
}
