package pl.api.itoffers.provider.justjoinit;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.List;
import java.util.UUID;

@Repository
public interface JustJoinItRepository extends MongoRepository<JustJoinItRawOffer, UUID> {
    List<JustJoinItRawOffer> findByScrapingId(UUID scrapingId);

    @Query("{ $and: [ { \"offer.title\": ?0, \"offer.slug\": ?1, \"offer.companyName\": ?2,  \"offer.publishedAt\": ?3} ] }")
    List<JustJoinItRawOffer> findDuplicatedOffers(String title, String slug, String companyName, String publishedAt);

    @Query("{ $and: [ { \"offer.title\": ?0, \"offer.slug\": ?1, \"offer.companyName\": ?2,  \"offer.experienceLevel\": ?3} ] }")
    List<JustJoinItRawOffer> findOriginatedRawOffers(String title, String slug, String companyName, String seniority);
}
