package com.restaurantwebsite.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class WebsiteBackendApplication {

	public static void main(String[] args) {
		// Force the JVM's default timezone to Europe/Berlin from day one, so
		// every LocalDateTime.now() call (reservation/order timestamps) reflects
		// the restaurant's actual local time regardless of where the server
		// eventually runs (e.g. Railway's US-region container).
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));

		SpringApplication.run(WebsiteBackendApplication.class, args);
	}

}