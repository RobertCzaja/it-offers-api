package pl.api.itoffers.offer.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.api.itoffers.offer.domain.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
