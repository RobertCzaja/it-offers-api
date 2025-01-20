package pl.api.itoffers.provider.justjoinit.ui.cli;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.service.FetchJustJoinItOffersService;

@Slf4j
@EnableScheduling
@Service
@RequiredArgsConstructor
public class FetchJustJoinItOffersScheduledJob {
  private final FetchJustJoinItOffersService fetchJustJoinItOffersService;

  @Scheduled(cron = "0 0 9 * * *")
  public void fetchJustJoinIt() {
    log.info("[jjit] start import");
    fetchJustJoinItOffersService.fetch("");
    log.info("[jjit] successfully imported");
  }
}
