package pl.api.itoffers.report;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

@Configuration
public class ImportStatisticsCollectorConfig {
  @Bean
  public ImportStatisticsCollector importStatisticsCollector() {
    return new ImportStatisticsCollector(
        new ClockInterface() {}, LoggerFactory.getLogger("DefaultLogger"));
  }
}
