package pl.api.itoffers.provider.justjoinit.ui.cli;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.model.CronLog;
import pl.api.itoffers.provider.justjoinit.repository.CronLogRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@EnableScheduling
@Service
@RequiredArgsConstructor
public class CronLogScheduledJob {
    private final CronLogRepository repository;

    @Scheduled(cron="0 0 0 24 12 ?")
    public void fetchJustJoinIt() {
        log.info("[cron-log] executed");
        repository.save(new CronLog(UUID.randomUUID(), LocalDateTime.now()));
    }
}
