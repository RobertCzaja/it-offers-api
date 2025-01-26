package pl.api.itoffers.provider.justjoinit.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

@Repository
public interface JustJoinItRepository extends MongoRepository<JustJoinItRawOffer, UUID> {
  /**
   * @deprecated
   */
  List<JustJoinItRawOffer> findByScrapingId(UUID scrapingId);

  List<JustJoinItRawOffer> findByScrapingIdAndTechnology(UUID scrapingId, String technology);

  @Query(
      "{ $and: [ { \"offer.title\": ?0, \"offer.slug\": ?1, \"offer.companyName\": ?2,  \"offer.publishedAt\": ?3} ] }")
  List<JustJoinItRawOffer> findDuplicatedOffers(
      String title, String slug, String companyName, String publishedAt);

  @Query(
      "{ $and: [ { \"offer.title\": ?0, \"offer.slug\": ?1, \"offer.companyName\": ?2,  \"offer.experienceLevel\": ?3} ] }")
  List<JustJoinItRawOffer> findOriginatedRawOffers(
      String title, String slug, String companyName, String seniority);
}
