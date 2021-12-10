package kr.or.cmcnu.bucbot.scheduler;

import com.google.common.base.Strings;
import kr.or.cmcnu.bucbot.component.EventQueue;
import kr.or.cmcnu.bucbot.controller.ChatController;
import kr.or.cmcnu.bucbot.pojo.edge.ChatMessage;
import kr.or.cmcnu.bucbot.pojo.event.TeamupEventChat;
import kr.or.cmcnu.bucbot.pojo.event.TeamupEventFeed;
import kr.or.cmcnu.bucbot.template.teamup.EdgeTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;

/**
 * Created by thisno on 2016-04-12.
 */

@CommonsLog
@Component
public class ChatScheduler {

    @Autowired
    @Qualifier("chatEventQueue")
    private EventQueue<TeamupEventChat> chatEventQueue;

    @Autowired
    private EdgeTemplate edgeTemplate;

    @Autowired
    private ChatController chatController;

    @Scheduled(fixedDelay = 100)
//   @Scheduled(cron="*/5 * * * * *")
    public void chat() throws UnsupportedEncodingException {
        TeamupEventChat teamupEventChat = chatEventQueue.poll();
        if (teamupEventChat == null) {
            return;
        }

        log.debug("chat pop!!!!!!!!!!!!!!!!!!!!!!!!!");
     

        ChatMessage chatMsg = edgeTemplate.getMessage(teamupEventChat.getRoom(), teamupEventChat.getMsg());
        if (chatMsg == null) {
            return;
        }

        // 길이가 다르면 장문 이니 long message 로 가지고 온다
        if (chatMsg.getContent().length() != chatMsg.getLen()) {
            String content = edgeTemplate.getMessageLong(teamupEventChat.getRoom(), teamupEventChat.getMsg());
            if (Strings.isNullOrEmpty(content) == false) {
                chatMsg.setContent(content);
            }
        }

        chatController.chat(chatMsg, teamupEventChat.getRoom());
    }

}
