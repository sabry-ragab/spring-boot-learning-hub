package com.kfh.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EducationHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducationHubApplication.class, args);
	}

}
