package pojo.event;


import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Session extends Event {

    private LinkedList<Talk> talks = new LinkedList<>();

    private List<Talk> allTalks;

    private boolean isFull;

    public Session(LocalTime startTime, Event nextEvent, List<Talk> allTalks) {
        super.setStartTime(startTime);
        super.setNextEvent(nextEvent);
        this.allTalks = allTalks;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public LinkedList<Talk> getTalks() {
        return talks;
    }

    @Override
    public boolean check() {
        return isFull();
    }

    @Override
    public boolean add(Event event) {
        Talk talk = talks.peekLast();
        event.setStartTime(talk == null ? getStartTime() : talk.getEndTime());
        event.setEndTime(event.getStartTime().plusMinutes(event.getDuration()));
        Event nextEvent = getNextEvent();
        nextEvent.setPreEvent(event);
        boolean check = getNextEvent().check();
        if (check) {
            talks.add((Talk) event);
            ((Talk) event).setUsed(true);
            Optional<Long> minDuration = allTalks.stream().filter(r -> !r.isUsed())
                    .min(Comparator.comparing(Event::getDuration)).map(Event::getDuration);
            setFull(!minDuration.isPresent() ||
                    nextEvent.getStartTime() != null && event.getEndTime().plusMinutes(minDuration.get()).isAfter(nextEvent.getStartTime()));
        }
        nextEvent.setPreEvent(talks.peekLast());
        return check;
    }

    @Override
    public void clear() {
        if (isFull) {
            getNextEvent().clear();
        }
        setFull(false);
        talks.removeLast().clear();
    }

    @Override
    public int getTotalEventsNum() {
        return getTalks().size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (super.getPreEvent() != null) {
            sb.append(super.getPreEvent().toString()).append("\r\n");
        }
        talks.forEach(r -> {
            sb.append(r.toString()).append("\r\n");
        });
        if (super.getNextEvent() != null) {
            sb.append(super.getNextEvent().toString()).append("\r\n");
        }
        return sb.toString();
    }
}
