package pl.api.itoffers.provider.justjoinit.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * TODO Should be removed after testing od https://github.com/RobertCzaja/it-offers/issues/56
 */
@Data
@RequiredArgsConstructor
@Document
public class CronLog {
    @Field("_id")
    private ObjectId id;
    private final UUID processId;
    private final LocalDateTime createdAt;
}
