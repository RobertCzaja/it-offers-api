package pl.api.itoffers.offer.infrastructure;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;

@Repository
public class TechnologyInMemoryRepository implements TechnologyRepository {
  @Override
  public List<String> allActive() {
    return Arrays.asList("php", "java", "javascript", "net", "devops", "go", "admin");
  }
}
