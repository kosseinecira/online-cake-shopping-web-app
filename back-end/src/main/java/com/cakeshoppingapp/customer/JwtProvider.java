package com.cakeshoppingapp.customer;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Repository;

@Repository
public class JwtProvider {

	private JwtEncoder jwtEncoder; // JWT ENCODE

	public JwtProvider(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

	public String createToken(Authentication authentication) {
		Instant now = Instant.now();
		long expiresIn = 2;

		String authorities = authentication.getAuthorities().stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.joining(" "));
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now).expiresAt(now.plusSeconds(expiresIn))
				.subject(authentication.getName()).claim("authorities", authorities).build();
		return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

	}

}
