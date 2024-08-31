package pl.api.itoffers.offer.infrastructure;


import org.springframework.stereotype.Repository;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;

import java.util.Arrays;
import java.util.List;

@Repository
public class TechnologyInMemoryRepository implements TechnologyRepository {
    @Override
    public List<String> allActive() {
        return Arrays.asList(
                "php",
                "java",
                "javascript",
                "net",
                "devops",
                "go"
        );
    }
}
