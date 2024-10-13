package pl.api.itoffers.provider.justjoinit.model;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@Document
public class JustJoinItRawOffer {
    @Field("_id")
    private ObjectId id;
    private final UUID scrapingId;
    private final String technology;
    private final Map<String, Object> offer;
    private final LocalDateTime createdAt;
}
