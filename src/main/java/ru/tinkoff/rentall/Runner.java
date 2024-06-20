package ru.tinkoff.rentall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"))
public class Runner {
	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
