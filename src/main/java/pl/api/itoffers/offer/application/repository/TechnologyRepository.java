package pl.api.itoffers.offer.application.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository {
  List<String> allActive();
}
