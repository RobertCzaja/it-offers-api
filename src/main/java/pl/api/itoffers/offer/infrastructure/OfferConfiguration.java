package pl.api.itoffers.offer.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.api.itoffers.offer.application.repository.OfferRepository;

@Configuration
public class OfferConfiguration {
    @Autowired
    private OfferRepository repository;

    @Bean
    @Primary
    public OfferRepository repository() { return repository; }
}
