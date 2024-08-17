package com.cakeshoppingapp.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.Endpoint;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfig {

	@Value("${api.endpoint.base-url}")
	private String BASE_URL;

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
		// this will will handle null value if the base url is not configured in
		// application.properties file
		this.BASE_URL = (BASE_URL == null) || (BASE_URL.isEmpty()) ? "" : BASE_URL;

		// Here we configure Authorization rules

		return http.authorizeHttpRequests(
				request -> request.requestMatchers(HttpMethod.GET, BASE_URL + "/cakes/**").permitAll()
						// categories
						// .requestMatchers(HttpMethod.POST, BASE_URL +
						// "/categories").hasAuthority("ROLE_ADMIN")
						// .requestMatchers(HttpMethod.PUT, BASE_URL +
						// "/categories/**").hasAuthority("ROLE_ADMIN")
						// .requestMatchers(HttpMethod.DELETE, BASE_URL +
						// "/categories/**").hasAuthority("ROLE_ADMIN")
						.requestMatchers(HttpMethod.GET, BASE_URL + "/categories/**").permitAll()
						// .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1")).permitAll()
						// // flavors
						// .requestMatchers(HttpMethod.POST, BASE_URL +
						// "/flavors/**").hasAuthority("ROLE_ADMIN")
						// .requestMatchers(HttpMethod.PUT, BASE_URL +
						// "/flavors/**").hasAuthority("ROLE_ADMIN")
						// .requestMatchers(HttpMethod.DELETE, BASE_URL +
						// "/flavors/**").hasAuthority("ROLE_ADMIN")
						// // /flavors + /flavors/**
						.requestMatchers(HttpMethod.GET, BASE_URL + "/flavors/**").permitAll()
						// // cakes

						// // users
						// .requestMatchers(HttpMethod.GET, BASE_URL +
						// "/users/**").hasAnyAuthority("ROLE_ADMIN")
						// .requestMatchers(HttpMethod.POST, BASE_URL +
						// "/users").hasAuthority("ROLE_ADMIN")
						// .requestMatchers(HttpMethod.PUT, BASE_URL +
						// "/users/**").hasAuthority("ROLE_ADMIN")
						// .requestMatchers(HttpMethod.DELETE, BASE_URL +
						// "/users/**").hasAuthority("ROLE_ADMIN")

						// .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll().anyRequest()
						// .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1")).permitAll().anyRequest()

						// .requestMatchers(HttpMethod.GET, BASE_URL + "/categories/**").permitAll()
						.requestMatchers(HttpMethod.GET, BASE_URL + "/images/**").permitAll()
						.requestMatchers(EndpointRequest.to("health", "info")).permitAll()
						.requestMatchers(EndpointRequest.toAnyEndpoint().excluding("health", "info"))
						.hasAuthority("ROLE_ADMIN")
						.anyRequest()
						.authenticated())
				.headers(header -> header.frameOptions().disable()).csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults()).oauth2ResourceServer(o -> o.jwt())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(Customizer.withDefaults()).build();

	}

	// @Bean
	// CorsConfigurationSource corsConfigurationSource() {
	// CorsConfiguration configuration = new CorsConfiguration();
	// configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
	// configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT",
	// "DELETE"));
	// UrlBasedCorsConfigurationSource source = new
	// UrlBasedCorsConfigurationSource();
	// source.registerCorsConfiguration("/**", configuration);
	// return source;
	// }
	// @Bean
	// CorsConfiguration corsConfiguration() {
	// return new CorsConfiguration().applyPermitDefaultValues();
	// }

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}

	// Used by Spring Security if CORS is enabled.
	// @Bean
	// CorsFilter corsFilter() {
	// UrlBasedCorsConfigurationSource source = new
	// UrlBasedCorsConfigurationSource();
	// CorsConfiguration config = new CorsConfiguration();
	// config.setAllowCredentials(true);
	// config.addAllowedOrigin("*");
	// config.addAllowedHeader("*");
	// config.addAllowedMethod("*");
	// source.registerCorsConfiguration("/**", config);
	// return new CorsFilter(source);
	// }

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
