package pl.api.itoffers.shared.utils.clock;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Primary
public interface ClockInterface {

    default Date currentDate() {
        return new Date();
    }
}
