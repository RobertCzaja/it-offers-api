package pl.api.itoffers.unit.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.api.itoffers.offer.application.service.TechnologiesProvider;
import pl.api.itoffers.offer.infrastructure.TechnologyInMemoryRepository;

public class TechnologiesProviderTest {

  @Test
  void whenTechnologyIsNotSpecifiedShouldReturnDefaultTechnologies() {
    var provider = new TechnologiesProvider(new TechnologyInMemoryRepository());

    assertThat(provider.getTechnologies(null)).hasSize(7);
    assertThat(provider.getTechnologies("")).hasSize(7);
  }
}
