package com.example.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.example")
@EntityScan(basePackages = "com.example.entities")
@EnableJpaRepositories(basePackages = "com.example.repositories")
@EnableAspectJAutoProxy
public class TrainingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingProjectApplication.class, args);
		
	}

}
