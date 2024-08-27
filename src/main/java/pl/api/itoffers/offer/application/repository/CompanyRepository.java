package pl.api.itoffers.offer.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.api.itoffers.offer.domain.Company;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
