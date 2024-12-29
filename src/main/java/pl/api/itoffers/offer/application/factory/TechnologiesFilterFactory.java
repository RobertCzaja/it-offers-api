package pl.api.itoffers.offer.application.factory;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;

@Service
@RequiredArgsConstructor
public class TechnologiesFilterFactory {

  private final TechnologyRepository technologyRepository;

  public List<String> get(String... technologies) {
    List<String> technologiesList =
        Arrays.asList(null == technologies ? new String[] {} : technologies);
    return technologiesList.isEmpty() ? technologyRepository.allActive() : technologiesList;
  }
}
