package pl.api.itoffers.helper;

import java.time.LocalDateTime;
import java.util.Date;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

public class FrozenClock implements ClockInterface {

  private Date currentDate = new Date();

  public void setCurrentDate(Date currentDate) {
    this.currentDate = currentDate;
  }

  @Override
  public Date currentDate() {
    return this.currentDate;
  }

  @Override
  public LocalDateTime now() {
    return LocalDateTime.of(2025, 1, 10, 17, 28, 5);
  }
}
