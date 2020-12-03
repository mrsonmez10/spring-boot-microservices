package com.mrsonmez.bookrating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRatingApplication.class, args);
	}

}
