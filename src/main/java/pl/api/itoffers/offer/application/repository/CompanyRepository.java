package pl.api.itoffers.offer.application.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.api.itoffers.offer.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
  Company findByName(String name);
}
