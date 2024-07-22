package com.cakeshoppingapp.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cakeshoppingapp.system.Result;
import com.cakeshoppingapp.system.StatusCode;

@RestController
public class AuthenticationContoller {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationContoller.class);
	private final AuthenticationService authenticationService;

	public AuthenticationContoller(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login")
	// This method return the basic user Login Information
	public Result getLoginInfo(Authentication authentication) {
		logger.debug("Authenticated User: {} ", authentication.getName());
		return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token",
				authenticationService.createLoginInfo(authentication));
	}

}
