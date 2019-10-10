package pojo.event;


import pojo.unit.PartlyTimeType;
import utils.CommonUtils;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PartlyTimeFixedEvent extends Event {

    private Byte type;
    private LocalTime[] range;
    private List<Talk> events;

    public PartlyTimeFixedEvent(String topic, Byte type, LocalTime[] range, List<Talk> events) {
        super.setTopic(topic);
        this.type = type;
        this.range = range;
        this.events = events;
    }

    public List<Talk> getEvents() {
        return events;
    }

    public void setEvents(List<Talk> events) {
        this.events = events;
    }

    @Override
    public boolean check() {
        PartlyTimeType partlyTimeType = PartlyTimeType.fromCode(this.type);
        switch (partlyTimeType) {
            case STRATTIME_UNKONW:
                if (assumeStartTime()) {
                    setStartTime(getPreEvent().getEndTime());
                }
                return !getPreEvent().getEndTime().isAfter(range[1]);
            case ENDTIME_UNKONW:
                boolean b1 = getPreEvent() != null && getDuration() != null
                        && CommonUtils.between(getPreEvent().getEndTime().plusMinutes(getDuration()), this.range[0], this.range[1]);
                if (b1) {
                    setEndTime(getNextEvent().getStartTime());
                }
                return !getPreEvent().getEndTime().plusMinutes(getDuration()).isAfter(range[1]);
            case BOTHTIME_UNKONW:
                boolean b2 = getPreEvent() != null && getNextEvent() != null
                        && CommonUtils.between(getPreEvent().getEndTime(), this.range[0], this.range[1])
                        && CommonUtils.between(getNextEvent().getStartTime(), this.range[2], this.range[3]);
                if (b2) {
                    setStartTime(getPreEvent().getEndTime());
                    setEndTime(getNextEvent().getStartTime());
                }
                return !getNextEvent().getStartTime().isBefore(range[2]) && !getPreEvent().getEndTime().isAfter(range[1]);
            default:
                return false;
        }
    }

    private boolean assumeStartTime() {
        Optional<Long> minDuration = events.stream().filter(r -> !r.isUsed() && !getPreEvent().getTopic().equals(r.getTopic()))
                .min(Comparator.comparing(Event::getDuration)).map(Event::getDuration);
        return getPreEvent() != null
                && (CommonUtils.between(getPreEvent().getEndTime(), this.range[0], this.range[1]))
                && (!minDuration.isPresent() || getPreEvent().getEndTime().plusMinutes(minDuration.get()).isAfter(range[1]));
    }

    @Override
    public boolean add(Event event) {
        return true;
    }

    @Override
    public void clear() {
        this.setStartTime(null);
        this.setEndTime(null);
        this.setNextEvent(null);
        this.setPreEvent(null);
    }

    @Override
    public int getTotalEventsNum() {
        return 1;
    }

}
