package com.enrollment.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Entry point. {@code @SpringBootApplication} turns on component scanning (finds every
 * {@code @Service}, {@code @RestController}, {@code @Configuration}... in this package and below)
 * and auto-configuration (sets up Tomcat, Jackson, Actuator, etc. based on what's on the classpath).
 */
@SpringBootApplication
@EnableCaching
public class EnrollmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnrollmentServiceApplication.class, args);
	}

}
