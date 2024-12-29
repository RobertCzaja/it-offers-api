package pl.api.itoffers.shared.utils.clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfig {
  @Bean
  public ClockInterface clock() {
    return new ClockInterface() {};
  }
}
