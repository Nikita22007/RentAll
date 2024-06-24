package ru.tinkoff.rentall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Runner {
	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}
}
