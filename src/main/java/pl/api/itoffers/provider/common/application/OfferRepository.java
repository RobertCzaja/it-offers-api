package pl.api.itoffers.provider.common.application;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.provider.common.domain.Offer;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {
}
