package com.lopezmorales.app.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ExamenCajeroAutomaticoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExamenCajeroAutomaticoApplication.class, args);
	}
	//http://localhost:8080/atm/login?logout
	//http://localhost:8080/atm/home
	//http://localhost:8080/atm/denominaciones
}
