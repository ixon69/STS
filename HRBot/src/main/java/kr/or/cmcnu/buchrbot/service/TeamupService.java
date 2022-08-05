package kr.or.cmcnu.buchrbot.service;


import lombok.extern.apachecommons.CommonsLog;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import kr.or.cmcnu.buchrbot.jdbc.customer.dao.CustomerDAO;
import kr.or.cmcnu.buchrbot.pojo.event.TeamupEventChat;
import kr.or.cmcnu.buchrbot.scheduler.TeamupScheduler;
import kr.or.cmcnu.buchrbot.template.teamup.EdgeTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Created by thisno on 2016-04-12.
 */
@CommonsLog
@Service
public class TeamupService {
ApplicationContext context1 = new ClassPathXmlApplicationContext("Spring-Module.xml");
CustomerDAO customerDAO = (CustomerDAO)this.context1.getBean("customerDAO");
	@Autowired
	EdgeTemplate edgeService;
	@Autowired 
	ChatService chatService;
	static final String DATE_FORMAT = "MM월 dd일 (E) hh시 mm분 ";
	static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    int feedGroup;  
    public String writeFeed(boolean isTotal) {
    	log.debug("==테스트==");
    	String nowDate = sdf.format(Calendar.getInstance().getTime( ));
    	String content ="== 조회시간은"+nowDate+ "==\n";;
    	
    	log.debug(content);
    	String msg1=this.customerDAO.getSvrHealth();
    	
    	
    	if(msg1.equals("AP가 정상동작중입니다.")){
    		log.debug(msg1);
    	}else{
    		  edgeService.createFeed(56253, 1,content+msg1);
    	    	// 메일서버 모니터링 피드번호
    	}
    	
        return content;
    }
    
    public String writePACSFeed(boolean isTotal) {
    	log.debug("==PACS피드 테스트==");
    	String nowDate = sdf.format(Calendar.getInstance().getTime( ));
    	String content ="== 조회시간은"+nowDate+ "==\n";;
    	
    	log.debug(content);
        String msg1=""; //chatService.getPacsSvrHealthAll();
    	
    	
    	if(msg1.contains("정상동작")){
    		log.debug(msg1);
    		 // edgeService.createFeed(57869, 1,content+msg1);
    	}else{
    		  edgeService.createFeed(57869, 1,content+msg1);
    	    	// 메일서버 모니터링 피드번호
    	}
    	
        return content;
    }
    
   
}
