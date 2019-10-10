package handler;


import org.junit.Test;
import pojo.event.Conference;
import pojo.event.PartlyTimeFixedEvent;
import pojo.event.Talk;
import pojo.event.WholeTimeFixedEvent;
import pojo.unit.PartlyTimeType;
import pojo.unit.TimeUnit;

import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static utils.CommonUtils.readLines;
import static utils.CommonUtils.removeExistFile;

public class ConferenceArrangeHandlerTest {



    @Test
    public void listAllArranges() {
        WholeTimeFixedEvent lunchEvent = new WholeTimeFixedEvent("Lunch", LocalTime.of(12, 0), LocalTime.of(13, 0));
        List<Talk> events = readLines("input.txt").stream()
                .map(ConferenceArrangeHandlerTest::getSingleTalk)
                .collect(Collectors.toList());
        PartlyTimeFixedEvent networkEvent = new PartlyTimeFixedEvent("Networking Event", PartlyTimeType.STRATTIME_UNKONW.getCode(),
                new LocalTime[]{LocalTime.of(16, 0), LocalTime.of(17, 0)}, events);

        removeExistFile("output.txt");

        ConferenceArrangeHandler conferenceArrangeHandler = new ConferenceArrangeHandler();
        conferenceArrangeHandler.listAllArranges(new Conference(LocalTime.of(9, 0), lunchEvent, networkEvent, events));
    }


    private static Talk getSingleTalk(String line) {
        String duration = line.substring(line.lastIndexOf(" ") + 1);
        Matcher matcher = Pattern.compile("\\d+").matcher(duration);
        int value = 1;
        int index = 0;
        if (matcher.find()) {
            value = parseInt(matcher.group());
            index = matcher.end();
        }
        TimeUnit timeUnit = TimeUnit.fromCode(duration.substring(index));
        Talk talk = new Talk((long) (timeUnit.getValue() * value));
        talk.setTopic(line);
        return talk;
    }



}