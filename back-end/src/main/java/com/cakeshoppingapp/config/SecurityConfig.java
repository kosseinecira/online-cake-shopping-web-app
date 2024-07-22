package com.cakeshoppingapp.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfig {

	private String baseUrl;
	private final RSAPublicKey publicKey;
	private final RSAPrivateKey privateKey;

	public SecurityConfig() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair keyPair = generator.generateKeyPair();
		this.publicKey = (RSAPublicKey) keyPair.getPublic();
		this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// Here we configure Authorization rules
		return http.authorizeHttpRequests(request -> request
				// flavors
				.requestMatchers(HttpMethod.POST, "/flavors/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.PUT, "/flavors/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/flavors/**").hasAuthority("ROLE_ADMIN")
				// /flavors + /flavors/**
				.requestMatchers(HttpMethod.GET, "/flavors/**").permitAll()
				// cakes
				.requestMatchers(HttpMethod.GET, "/cakes").permitAll()
				// users
				.requestMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.POST, "/users").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.PUT, "/users/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll().anyRequest()
				.authenticated()).headers(header -> header.frameOptions().disable()).csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults())

				.oauth2ResourceServer(o -> o.jwt())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.build(); // For H2 BROWSER CONSOLE ACCESS
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	JwtEncoder jwtEncoder() {
		//
		JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
		JWKSource<SecurityContext> JwkSet = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(JwkSet);
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}
}
