package pl.api.itoffers.provider.justjoinit.service.extractor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.api.itoffers.provider.justjoinit.service.extractor.v1.JustJoinItOffersFetcherV1;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.JustJoinItOffersFetcherV2;

@Configuration
@RequiredArgsConstructor
public class JustJoinItFetcherConfig {
    private final JustJoinItOffersFetcherV1 justJoinItOffersFetcherV1;
    private final JustJoinItOffersFetcherV2 justJoinItOffersFetcherV2;

    /** @deprecated since december 2024*/
    @Bean
    public JustJoinItOffersFetcherV1 v1() { return justJoinItOffersFetcherV1; }
    @Bean
    @Primary
    public JustJoinItOffersFetcherV2 v2() { return justJoinItOffersFetcherV2; }
}
