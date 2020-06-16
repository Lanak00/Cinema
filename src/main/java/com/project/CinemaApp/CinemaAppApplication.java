package com.project.CinemaApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.project.CinemaApp"})
public class CinemaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaAppApplication.class, args);
	}
}
