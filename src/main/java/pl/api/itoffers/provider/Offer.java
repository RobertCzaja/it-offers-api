package pl.api.itoffers.provider;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

// TODO to remove, only for purpose of testing connection to local MongoDB
@Document
@Data
@RequiredArgsConstructor
public class Offer {
    @Id
    private final String id;

    private final String tile;
}
