package pl.api.itoffers.offer.application;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.offer.domain.Offer;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {
}
