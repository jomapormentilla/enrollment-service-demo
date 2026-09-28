package com.enrollment.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * With spring-boot-starter-security on the classpath, Boot locks every endpoint behind a generated
 * login. Defining our own SecurityFilterChain bean replaces that default.
 *
 * Phase 1 leaves everything open so curl works. A real service would authenticate here
 * (e.g. OAuth2 JWT via .oauth2ResourceServer(...), or mTLS between services).
 */
@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// CSRF protects browser sessions that use cookies. A stateless JSON API has no session cookie to protect.
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		return http.build();
	}
}
