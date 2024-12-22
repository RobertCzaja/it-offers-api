package pl.api.itoffers.provider.justjoinit.service.extractor.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.JustJoinItConnector;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItOffersFetcher;

import java.util.ArrayList;
import java.util.Map;

/**
 * Valid between 08.2024 and 12.2024 then JJIT changed returned HTML structure, and it required V2 implementation
 */
@Service
@RequiredArgsConstructor
public class JustJoinItOffersFetcherV1 implements JustJoinItOffersFetcher
{
    private final JustJoinItConnector connector;
    private final JustJoinItPayloadExtractor payloadExtractor;

    @Override
    public ArrayList<Map<String, Object>> fetch(String technology) {
        return payloadExtractor.extract(
            connector.fetchStringifyJsonPayload(technology)
        );
    }
}
