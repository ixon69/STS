package kr.or.cmcnu.buchrbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.or.cmcnu.buchrbot.component.EventQueue;
import kr.or.cmcnu.buchrbot.pojo.event.TeamupEventChat;
import kr.or.cmcnu.buchrbot.pojo.event.TeamupEventFeed;

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
