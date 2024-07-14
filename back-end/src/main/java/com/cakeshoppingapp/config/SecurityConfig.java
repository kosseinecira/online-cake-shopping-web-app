package com.cakeshoppingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

	private String baseUrl;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		// Here we configure Authorization rules
		return http.authorizeHttpRequests(
				request -> request
				//flavors
				.requestMatchers(HttpMethod.POST, "/flavors/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.PUT, "/flavors/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/flavors/**").hasAuthority("ROLE_ADMIN")
				// /flavors + /flavors/**
				.requestMatchers(HttpMethod.GET, "/flavors/**").permitAll()
				//cakes
				.requestMatchers(HttpMethod.GET, "/cakes").permitAll()
				//users
				.requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.POST, "/users").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.PUT, "/users/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
				.anyRequest().authenticated()
				)
				.headers(header -> header.frameOptions().disable())
			//	.csrf(csrf->csrf.disable())
				.httpBasic(Customizer.withDefaults())
				.build(); // For H2 BROWSER CONSOLE ACCESS
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	
}
