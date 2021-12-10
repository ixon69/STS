package kr.or.cmcnu.ixon.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WebDevelopment1Application {

	public static void main(String[] args) {
		SpringApplication.run(WebDevelopment1Application.class, args);
	}

}
