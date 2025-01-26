package pl.api.itoffers.offer.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;

@Service
@RequiredArgsConstructor
public class TechnologiesProvider {

  private final TechnologyRepository technologyRepository;

  public List<String> getTechnologies(String requestedTechnology) {
    return null == requestedTechnology || requestedTechnology.isEmpty()
        ? technologyRepository.allActive()
        : List.of(requestedTechnology);
  }
}
