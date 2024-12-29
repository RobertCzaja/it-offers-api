package pl.api.itoffers.shared.utils.clock;

import java.util.Date;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public interface ClockInterface {

  default Date currentDate() {
    return new Date();
  }
}
