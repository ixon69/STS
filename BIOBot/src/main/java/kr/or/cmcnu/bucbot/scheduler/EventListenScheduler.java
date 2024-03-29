package kr.or.cmcnu.bucbot.scheduler;

import kr.or.cmcnu.bucbot.component.EventQueue;
import kr.or.cmcnu.bucbot.pojo.event.TeamupEvent;
import kr.or.cmcnu.bucbot.pojo.event.TeamupEventChat;
import kr.or.cmcnu.bucbot.pojo.event.TeamupEventFeed;
import kr.or.cmcnu.bucbot.pojo.event.TeamupEventList;
import kr.or.cmcnu.bucbot.template.teamup.EventTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by thisno on 2016-04-12.
 */

@CommonsLog
@Component
public class EventListenScheduler {

    @Autowired
    @Qualifier("chatEventQueue")
    private EventQueue<TeamupEventChat> chatEventQueue;

    @Autowired
    @Qualifier("feedEventQueue")
    private EventQueue<TeamupEventFeed> feedEventQueue;

    @Autowired
    private EventTemplate eventTemplate;

    @Scheduled(fixedDelay = 10)
    public void getEvent() {
        log.debug("waiting get event...");
        TeamupEventList teamupEventList = eventTemplate.getEvent();
        if (teamupEventList == null) {
            return;
        }

        List<TeamupEvent> events = teamupEventList.getEvents();
        if (events == null || events.isEmpty()) {
            return;
        }

        events.forEach(e -> {
            doEvent(e);
        });
    }

    private void doEvent(TeamupEvent ev) {
        String t = ev.getType();
        switch (t) {
            case "chat.message":
                chatEventQueue.offer(ev.getChat());
                break;

            // TODO feed
            case "feed.feed":
            case "feed.reply":
                TeamupEventFeed teamupEventFeed = ev.getFeed();
                teamupEventFeed.setType(t);
                feedEventQueue.offer(teamupEventFeed);
                break;

            default:
                break;
        }
    }
}
