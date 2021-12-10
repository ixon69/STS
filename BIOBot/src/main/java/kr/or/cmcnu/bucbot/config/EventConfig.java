package kr.or.cmcnu.bucbot.config;

import kr.or.cmcnu.bucbot.component.EventQueue;
import kr.or.cmcnu.bucbot.pojo.event.TeamupEventChat;
import kr.or.cmcnu.bucbot.pojo.event.TeamupEventFeed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by thisno on 2016-10-27.
 */

@Configuration
public class EventConfig {

    @Bean(name = "chatEventQueue")
    public EventQueue<TeamupEventChat> getChatEventQueue(){
        return new EventQueue<>();
    }

    @Bean(name = "feedEventQueue")
    public EventQueue<TeamupEventFeed> getFeedReplyEventQueue(){
        return new EventQueue<>();
    }
}
