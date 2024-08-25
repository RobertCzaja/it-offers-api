package pl.api.itoffers.provider.justjoinit.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Document
public class JustJoinItRawOffer {
    //@Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    //private UUID id;
    private UUID scrapingId;
    private String technology;
    private Map<String, Object> offer;
    private LocalDateTime createdAt;
}
