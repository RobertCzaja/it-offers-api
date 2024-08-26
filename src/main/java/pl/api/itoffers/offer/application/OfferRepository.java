package pl.api.itoffers.offer.application;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.offer.domain.Offer;

import java.util.UUID;

@Transactional
@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {

}
