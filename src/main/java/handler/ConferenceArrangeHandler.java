package handler;

import pojo.event.Conference;
import pojo.event.Talk;
import pojo.event.Track;

import java.util.List;

import static utils.CommonUtils.addContent;

public class ConferenceArrangeHandler {

    private int planNum;


    public ConferenceArrangeHandler() {
        this.planNum = 0;
    }

    public void listAllArranges(Conference conference) {
        List<Talk> events = conference.getEvents();
        if (checkArrangeFinished(conference, events.size())) {
            printConferenceArrange(conference);
            return;
        }
        for (int i = 0; i < events.size(); i++) {
            Talk talk = events.get(i);
            if (!talk.isUsed()) {
                if (conference.add(talk)) {
                    listAllArranges(conference);
                    talk.setUsed(false);
                    conference.clear();
                }
            }
        }
    }

    private boolean checkArrangeFinished(Conference conference, int size) {
        return conference.getTotalEventsNum() == size;
    }

    private void printConferenceArrange(Conference conference) {
        Track last = conference.getTracks().peekLast();
        if (last.check() || last.getTotalEventsNum() == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Plan " + (++planNum) + "").append("\r\n");
            sb.append(conference.toString());
            addContent("output.txt", sb.toString());
        }
    }
}
