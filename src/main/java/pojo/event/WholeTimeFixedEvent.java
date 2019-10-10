package pojo.event;

import java.time.Duration;
import java.time.LocalTime;

public class WholeTimeFixedEvent extends Event {

    public WholeTimeFixedEvent(String topic, LocalTime startTime, LocalTime endTime) {
        super.setTopic(topic);
        super.setStartTime(startTime);
        super.setEndTime(endTime);
        super.setDuration(Duration.between(startTime, endTime).toMinutes());
    }

    @Override
    public boolean check() {
        Event preEvent = getPreEvent();
        return !preEvent.getEndTime().isAfter(super.getStartTime());
    }

    @Override
    public boolean add(Event event) {
        return true;
    }

    @Override
    public void clear() {
        this.setPreEvent(null);
        this.setNextEvent(null);
    }

    @Override
    public int getTotalEventsNum() {
        return 1;
    }

}
