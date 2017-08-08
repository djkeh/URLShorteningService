package com.kakaopay.urlshortening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class UrlShorteningServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShorteningServiceApplication.class, args);
	}
}
