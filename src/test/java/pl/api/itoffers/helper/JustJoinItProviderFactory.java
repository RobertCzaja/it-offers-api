package pl.api.itoffers.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.api.itoffers.integration.provider.justjoinit.inmemory.JustJoinItInMemoryConnector;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItPayloadExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

@Service
public class JustJoinItProviderFactory {

    @Autowired
    private JustJoinItPayloadExtractor payloadExtractor;
    @Autowired
    private JustJoinItRepository repository;

    public JustJoinItProvider create() {
        return new JustJoinItProvider(
                new JustJoinItInMemoryConnector(new FileManager()),
                payloadExtractor,
                repository
        );
    }
}
