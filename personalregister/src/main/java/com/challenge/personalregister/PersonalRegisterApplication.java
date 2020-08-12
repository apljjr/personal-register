package com.challenge.personalregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PersonalRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalRegisterApplication.class, args);
	}

}
