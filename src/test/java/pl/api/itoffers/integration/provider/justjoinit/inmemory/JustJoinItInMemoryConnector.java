package pl.api.itoffers.integration.provider.justjoinit.inmemory;

import pl.api.itoffers.integration.provider.justjoinit.payload.JustJoinItParams;
import pl.api.itoffers.provider.justjoinit.JustJoinItConnector;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

import java.io.IOException;

public class JustJoinItInMemoryConnector implements JustJoinItConnector {

    private final FileManager fileManager;
    public String payloadPath = JustJoinItParams.ALL_LOCATIONS_PAYLOAD_PATH;

    public JustJoinItInMemoryConnector(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public String fetchStringifyJsonPayload(String technology) {
        try {
            return this.fileManager.readFile(payloadPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
