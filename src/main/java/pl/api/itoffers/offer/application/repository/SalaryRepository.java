package pl.api.itoffers.offer.application.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryId;

@Transactional
@Repository
public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
}
