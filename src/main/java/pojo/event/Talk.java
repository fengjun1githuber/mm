package pojo.event;

public class Talk extends Event {

    private boolean isUsed;


    public Talk(Long duration) {
        super.setDuration(duration);

    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public boolean add(Event event) {
        return true;
    }

    @Override
    public void clear() {
        setStartTime(null);
        setEndTime(null);
    }

    @Override
    public int getTotalEventsNum() {
        return 1;
    }
}
