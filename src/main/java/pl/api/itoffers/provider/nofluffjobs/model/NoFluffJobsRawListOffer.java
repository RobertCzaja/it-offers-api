package pl.api.itoffers.provider.nofluffjobs.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@RequiredArgsConstructor
@Document
public class NoFluffJobsRawListOffer {
  @Field("_id")
  private ObjectId id;

  private final UUID scrapingId;
  private final String technology;
  private final Map<String, Object> offer;
  private final LocalDateTime createdAt;
}
