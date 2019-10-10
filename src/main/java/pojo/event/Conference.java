package pojo.event;

import java.io.IOException;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import static utils.CommonUtils.deepClone;


public class Conference extends Event {

    private Event lunchEvent;
    private Event networkEvent;
    private List<Talk> events;
    private LinkedList<Track> tracks = new LinkedList<>();


    public Conference(LocalTime startTime, Event lunchEvent, Event networkEvent, List<Talk> events) {
        super.setStartTime(startTime);
        this.lunchEvent = lunchEvent;
        this.networkEvent = networkEvent;
        this.events = events;
    }


    public List<Talk> getEvents() {
        return events;
    }


    public LinkedList<Track> getTracks() {
        return tracks;
    }


    @Override
    public boolean check() {
        Track last = tracks.peekLast();
        boolean check = last != null && last.check();
        if (last == null || check) {
            try {
                PartlyTimeFixedEvent networkEventTemp = (PartlyTimeFixedEvent) deepClone(networkEvent);
                networkEventTemp.setEvents(((PartlyTimeFixedEvent)networkEvent).getEvents());
                Track track = new Track(super.getStartTime(), events, (Event) deepClone(lunchEvent), networkEventTemp);
                tracks.add(track);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    @Override
    public boolean add(Event event) {
        check();
        return tracks.peekLast().add(event);
    }

    @Override
    public void clear() {
        tracks.peekLast().clear();
        if (tracks.peekLast().getMorningSession().getTalks().size() == 0) {
            tracks.removeLast();
        }
    }

    @Override
    public int getTotalEventsNum() {
        int size = 0;
        for (Track track : this.getTracks()) {
            size += track.getTotalEventsNum();
        }
        return size;
    }

    @Override
    public String toString() {
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        for (Track track : getTracks()) {
            sb.append("Track " + (++cnt) + ":").append("\r\n");
            sb.append(track.toString());
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
