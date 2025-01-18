package pl.api.itoffers.offer.application.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.offer.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
  Company findByName(String name);
}
