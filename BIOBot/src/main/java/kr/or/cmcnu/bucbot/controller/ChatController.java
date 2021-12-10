package kr.or.cmcnu.bucbot.controller;

import kr.or.cmcnu.bucbot.pojo.edge.ChatMessage;
import kr.or.cmcnu.bucbot.service.ChatService;
import lombok.extern.apachecommons.CommonsLog;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by thisno on 2016-04-12.
 */

@CommonsLog
@Component
public class ChatController {

    @Autowired
    ChatService chatService;

    public void chat(ChatMessage chatMessage, int room) throws UnsupportedEncodingException{

        log.debug("chat : " + chatMessage.getContent());

        switch (chatMessage.getType()) {
            case 1: // chart
                chatService.doChat(chatMessage, room);
                break;
            case 2: // TODO file
                break;
            default:
                break;
        }

    }


}
