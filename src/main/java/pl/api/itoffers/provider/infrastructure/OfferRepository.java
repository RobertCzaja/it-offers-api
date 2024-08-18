package pl.api.itoffers.provider.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.provider.Offer;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {
}
