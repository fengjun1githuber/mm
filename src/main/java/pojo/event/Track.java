package pojo.event;

import java.time.LocalTime;
import java.util.List;

public class Track extends Event {

    private Session morningSession;
    private Session afternoonSession;

    public Track(LocalTime startTime, List<Talk> allTalks, Event lunchEvent, Event netWorkEvent) {
        super.setStartTime(startTime);
        morningSession = new Session(startTime, lunchEvent, allTalks);
        afternoonSession = new Session(lunchEvent.getEndTime(), netWorkEvent, allTalks);
    }

    public Session getMorningSession() {
        return morningSession;
    }

    public Session getAfternoonSession() {
        return afternoonSession;
    }

    @Override
    public boolean check() {
        return morningSession.check() && afternoonSession.check();
    }

    @Override
    public boolean add(Event event) {
        if (morningSession.check()) {
            return afternoonSession.add(event);
        }
        return morningSession.add(event);
    }

    @Override
    public void clear() {
        if (!morningSession.check() || afternoonSession.getTalks().size() == 0) {
            morningSession.clear();
        } else {
            afternoonSession.clear();
        }
    }

    @Override
    public int getTotalEventsNum() {
        return getMorningSession().getTotalEventsNum()
                + getAfternoonSession().getTotalEventsNum();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(morningSession.toString());
        sb.append(afternoonSession.toString());
        return sb.toString();
    }
}
