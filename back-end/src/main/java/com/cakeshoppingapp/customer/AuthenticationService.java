package com.cakeshoppingapp.customer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cakeshoppingapp.dtoes.AuthenticationDTO;

@Service
public class AuthenticationService {

	private JwtProvider jwtProvider;

	public AuthenticationService(JwtProvider jwtProvider) {
		super();
		this.jwtProvider = jwtProvider;
	}

	public Map<String, Object> createLoginInfo(Authentication authentication) {

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		AuthenticationDTO authenticationDTO = userDetailsImpl.getAuthenticationDTO();

		String token = this.jwtProvider.createToken(authentication);

		Map<String, Object> loginResultMap = new HashMap<>();
		loginResultMap.put("token", token);
		loginResultMap.put("user", authenticationDTO);

		return loginResultMap;
	}

}
