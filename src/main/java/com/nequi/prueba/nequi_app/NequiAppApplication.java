package com.nequi.prueba.nequi_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class NequiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NequiAppApplication.class, args);
	}

}
