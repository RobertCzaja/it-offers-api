package pl.api.itoffers.provider.common.infrastructure;


import pl.api.itoffers.provider.common.application.TechnologyRepository;

import java.util.Arrays;
import java.util.List;

public class TechnologyInMemoryRepository implements TechnologyRepository {
    @Override
    public List<String> allActive() {
        return Arrays.asList(
                "php",
                "java",
                "javascript",
                "net",
                "devops"
                //"go"
        );
    }
}
