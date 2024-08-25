package pl.api.itoffers.provider.justjoinit;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.UUID;

@Repository
public interface JustJoinItRepository extends MongoRepository<JustJoinItRawOffer, UUID> {
}
