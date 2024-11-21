package pl.api.itoffers.shared.logger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MongoDbLogger implements Logger {
    private final LogMessageRepository repository;

    @Override
    public void info(
        String context,
        String message
    ) {
        repository.save(new LogMessage(String.format("[%s] %s", context, message), LocalDateTime.now()));
    }
}
