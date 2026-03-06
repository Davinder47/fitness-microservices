package com.example.activityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.activityservice")
public class ActivityserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivityserviceApplication.class, args);
	}

}
