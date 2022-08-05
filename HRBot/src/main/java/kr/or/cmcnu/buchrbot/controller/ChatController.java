package kr.or.cmcnu.buchrbot.controller;

import lombok.extern.apachecommons.CommonsLog;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.or.cmcnu.buchrbot.pojo.edge.ChatMessage;
import kr.or.cmcnu.buchrbot.service.ChatService;

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
