package pl.api.itoffers.provider.justjoinit;

import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItPayloadExtractor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
public class JustJoinItProvider {

    private final JustJoinItConnector connector;
    private final JustJoinItPayloadExtractor payloadExtractor;
    private final JustJoinItRepository repository;

    public JustJoinItProvider(
            JustJoinItConnector connector,
            JustJoinItPayloadExtractor payloadExtractor,
            JustJoinItRepository repository
    ) {
        this.connector = connector;
        this.payloadExtractor = payloadExtractor;
        this.repository = repository;
    }

    public void fetch(String technology, UUID scrapingId)
    {
        String stringifyJsonPayload = connector.fetchStringifyJsonPayload(technology);
        ArrayList<Map<String, Object>> offers = payloadExtractor.extract(stringifyJsonPayload);
        offers.forEach(offer -> repository.save(new JustJoinItRawOffer(scrapingId,technology,offer,LocalDateTime.now())));
    }

}
