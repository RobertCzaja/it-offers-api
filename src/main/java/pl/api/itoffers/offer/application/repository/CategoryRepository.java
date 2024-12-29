package pl.api.itoffers.offer.application.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.api.itoffers.offer.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
  Category findByName(String name);
}
