package kr.or.cmcnu.buchrbot.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.or.cmcnu.buchrbot.jdbc.client.dao.ClientDAO;
import kr.or.cmcnu.buchrbot.jdbc.client.model.Client;
import kr.or.cmcnu.buchrbot.jdbc.common.StringUtil;
import kr.or.cmcnu.buchrbot.jdbc.customer.dao.CustomerDAO;
import kr.or.cmcnu.buchrbot.jdbc.itcuser.model.ItcUser;
import kr.or.cmcnu.buchrbot.pojo.edge.Button;
import kr.or.cmcnu.buchrbot.pojo.edge.ChatMessage;
import kr.or.cmcnu.buchrbot.pojo.edge.ExtraV2;
import kr.or.cmcnu.buchrbot.pojo.event.TeamupEventChat;
import kr.or.cmcnu.buchrbot.template.teamup.EdgeTemplate;

@Component
@EnableScheduling
public class ChatService {
	private static final Log log = LogFactory.getLog(ChatService.class);
	
    @Autowired
    private EdgeTemplate edgeTemplate;

    @Value("${project.version}")
    private String version;

    public void doWelcome(TeamupEventChat eventChat) { // 대화방 입장 시 실행
        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.textButton("lotto", "로또 번호 뽑기", "로또 번호 뽑아줘"));
        ExtraV2 extraV2 = new ExtraV2(null, null, buttons);

        edgeTemplate.say(eventChat.getRoom(), "안녕하세요.\n" + version, extraV2);
    }

    public void doChat(ChatMessage chatMessage, int room) {
        String content = chatMessage.getContent().trim();
        String responseId = chatMessage.getResponseId();
        log.info(chatMessage.getResponseId());
        if (content.equals("?")) {
            edgeTemplate.say(room, version);
        } else if (content.equals("로또") || "lotto".equals(responseId)) {
            List<Integer> number = new Random().ints(1, 45).distinct().limit(6).boxed().sorted()
                    .collect(Collectors.toList());

            List<Button> buttons = new ArrayList<>();
            buttons.add(Button.textButton("lotto", "로또 번호 뽑기", "로또 번호 뽑아줘"));
            ExtraV2 extraV2 = new ExtraV2(buttons, null, buttons);

            edgeTemplate.say(room, number.toString(), extraV2);
        }
    }
}
