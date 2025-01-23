package pl.api.itoffers.helper;

import java.time.LocalDateTime;
import java.util.Date;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

public class FrozenClock implements ClockInterface {

  private Date currentDate = new Date();
  private LocalDateTime now = LocalDateTime.of(2025, 1, 10, 17, 28, 5);

  public void setCurrentDate(Date currentDate) {
    this.currentDate = currentDate;
  }

  public void setNow(LocalDateTime newNow) {
    this.now = newNow;
  }

  @Override
  public Date currentDate() {
    return this.currentDate;
  }

  @Override
  public LocalDateTime now() {
    return this.now;
  }
}
