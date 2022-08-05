package kr.or.cmcnu.buchrbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@EnableScheduling
@SpringBootApplication
@ImportResource("classpath*:/applicationContext.xml")
public class HrBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrBotApplication.class, args);
        log.info("start~"); 
    }
}
