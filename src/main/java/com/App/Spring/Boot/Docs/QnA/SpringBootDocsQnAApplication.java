package com.App.Spring.Boot.Docs.QnA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootDocsQnAApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootDocsQnAApplication.class, args);
	}

}
