package com.mrsonmez.bookinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookInfoApplication.class, args);
	}

}
