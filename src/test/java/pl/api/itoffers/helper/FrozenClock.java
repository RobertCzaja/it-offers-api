package pl.api.itoffers.helper;

import pl.api.itoffers.shared.utils.clock.ClockInterface;

import java.util.Date;

public class FrozenClock implements ClockInterface {

    private Date currentDate = new Date();

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public Date currentDate() {
        return this.currentDate;
    }
}
