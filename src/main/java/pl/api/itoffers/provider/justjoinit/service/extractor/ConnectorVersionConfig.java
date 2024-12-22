package pl.api.itoffers.provider.justjoinit.service.extractor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.api.itoffers.provider.justjoinit.service.extractor.v1.JustJoinItHttpConnectorV1;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.JustJoinItHttpConnectorV2;

@Configuration
@RequiredArgsConstructor
public class ConnectorVersionConfig {
    private final JustJoinItHttpConnectorV1 justJoinItHttpConnectorV1;
    private final JustJoinItHttpConnectorV2 justJoinItHttpConnectorV2;

    @Bean
    @Primary
    public JustJoinItHttpConnectorV1 connectorV1() { return justJoinItHttpConnectorV1; }
    @Bean
    public JustJoinItHttpConnectorV2 connectionV2() { return justJoinItHttpConnectorV2; }
}
