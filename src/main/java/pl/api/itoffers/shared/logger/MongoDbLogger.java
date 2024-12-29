package pl.api.itoffers.shared.logger;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoDbLogger implements Logger {
  private final LogMessageRepository repository;

  @Override
  public void info(String context, String message) {
    repository.save(
        new LogMessage(String.format("[%s] %s", context, message), LocalDateTime.now()));
  }
}
