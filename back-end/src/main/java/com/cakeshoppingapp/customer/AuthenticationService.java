package com.cakeshoppingapp.customer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cakeshoppingapp.dtoes.AuthenticationDTO;

@Service
public class AuthenticationService {

	private final JwtProvider jwtProvider;
	private final CustomerService customerService;

	

	public AuthenticationService(JwtProvider jwtProvider, CustomerService customerService) {
		this.jwtProvider = jwtProvider;
		this.customerService = customerService;
	}



	public Map<String, Object> createLoginInfo(Authentication authentication) {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		AuthenticationDTO authenticationDTO = userDetailsImpl.getAuthenticationDTO();
		String token = this.jwtProvider.createToken(authentication);

		Map<String, Object> loginResultMap = new HashMap<>();
		loginResultMap.put("token", token);
		loginResultMap.put("user", authenticationDTO);
		System.out.println("AUTHENTICATION::::: " + authenticationDTO);
		System.out.println(token);
		return loginResultMap;
	}

}
