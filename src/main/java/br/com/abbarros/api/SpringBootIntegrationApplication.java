package br.com.abbarros.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
public class SpringBootIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootIntegrationApplication.class, args);
	}

}

