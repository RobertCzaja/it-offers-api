package pl.api.itoffers.shared.logger;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Document
public class LogMessage {
    @Field("_id")
    private ObjectId id;
    private final String message;
    private final LocalDateTime createdAt;
}
