package pojo.event;


import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public abstract class Event implements Serializable {

    private String topic;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long duration;
    private Event preEvent;
    private Event nextEvent;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Event getPreEvent() {
        return preEvent;
    }

    public void setPreEvent(Event preEvent) {
        this.preEvent = preEvent;
    }

    public Event getNextEvent() {
        return nextEvent;
    }

    public void setNextEvent(Event nextEvent) {
        this.nextEvent = nextEvent;
    }

    //与上一个event的比较
    public abstract boolean check();

    //加入对自身属性的补全
    public abstract boolean add(Event event);

    //重置
    public abstract void clear();

    //获取event的总数
    public abstract int getTotalEventsNum();

    @Override
    public String toString() {
        if (startTime == null) {
            return topic;
        } else {
            String desc = startTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.US)).concat(" ").concat(topic);
            return desc.indexOf(":") < 2 ? "0".concat(desc) : desc;
        }
    }

}
