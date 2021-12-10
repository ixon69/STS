package kr.or.cmcnu.bucbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@EnableScheduling
@SpringBootApplication
@ImportResource("classpath*:/applicationContext.xml")
public class BioBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioBotApplication.class, args);
        log.info("start~"); 
    }
}
