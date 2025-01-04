package com.example.Study.Hall.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
		scanBasePackages = "com.example.Study.Hall.Management",
		exclude = {DataSourceAutoConfiguration.class}
)
@EnableMongoRepositories(basePackages = "com.example.Study.Hall.Management.repository")
@EnableMongoAuditing
@EnableFeignClients
public class StudyHallManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyHallManagementApplication.class, args);
	}

}
