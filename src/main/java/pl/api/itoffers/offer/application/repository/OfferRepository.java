package pl.api.itoffers.offer.application.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.offer.domain.Offer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {

    @Query("SELECT o FROM Offer o WHERE o.technology IN :technologies AND o.createdAt BETWEEN :from AND :to")
    List<Offer> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to, String[] technologies);

    @Query("SELECT o FROM Offer o WHERE o.slug = :slug AND o.title = :title AND o.company.name = :companyName")
    Offer findByDifferentOffer(String slug, String title, String companyName);

    List<Offer> findByPublishedAt(LocalDateTime publishedAt);

    List<Offer> findAllByOrderBySlugAsc();
}
