package pl.api.itoffers.provider.nofluffjobs.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;

@Repository
public interface NoFluffJobsDetailsOfferRepository
    extends MongoRepository<NoFluffJobsRawDetailsOffer, UUID> {
  Optional<NoFluffJobsRawDetailsOffer> findByOfferId(UUID offerId);
}
