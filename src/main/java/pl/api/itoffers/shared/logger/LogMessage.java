package pl.api.itoffers.shared.logger;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@RequiredArgsConstructor
@Document
public class LogMessage {
  @Field("_id")
  private ObjectId id;

  private final String message;
  private final LocalDateTime createdAt;
}
