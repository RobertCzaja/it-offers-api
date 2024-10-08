package pl.api.itoffers.offer.application.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologiesFilterFactory {

    private final TechnologyRepository technologyRepository;

    public List<String> get(String[] technologies) {
        List<String> technologiesList = Arrays.asList(
            null == technologies
                ? new String[]{}
                : technologies
        );
        return technologiesList.isEmpty()
            ? technologyRepository.allActive()
            : technologiesList;
    }
}
