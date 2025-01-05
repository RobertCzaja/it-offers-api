package pl.api.itoffers.provider.nofluffjobs.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;

@Repository
public interface NoFluffJobsListOfferRepository
    extends MongoRepository<NoFluffJobsRawListOffer, UUID> {}
