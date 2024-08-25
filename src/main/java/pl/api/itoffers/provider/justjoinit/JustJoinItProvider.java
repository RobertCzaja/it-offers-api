package pl.api.itoffers.provider.justjoinit;

import org.bson.Document;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
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

        for (Map<String, Object> offer : offers) {
            JustJoinItRawOffer entity = new JustJoinItRawOffer();
            entity.setScrapingId(scrapingId);
            entity.setTechnology(technology);
            entity.setOffer(offer);
            entity.setCreatedAt(LocalDateTime.now());

            repository.save(entity);
        }
    }

}
