package pl.api.itoffers.provider.justjoinit.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@Document
public class JustJoinItRawOffer {
    private final UUID scrapingId;
    private final String technology;
    private final Map<String, Object> offer;
    private final LocalDateTime createdAt;
}
