package com.cakeshoppingapp.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cakeshoppingapp.system.Result;
import com.cakeshoppingapp.system.StatusCode;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationContoller {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationContoller.class);
	private final AuthenticationService authenticationService;

	public AuthenticationContoller(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login")
	// This method return the basic user Login Information
	public Result getLoginInfo(Authentication authentication) {
		logger.debug("Authenticated User: {} ", authentication.getName());
		return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token",
				authenticationService.createLoginInfo(authentication));
	}

//	@PostMapping(value="/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public Result createUser(@RequestBody(required = true) AuthenticationDTO authenticationDTO) {
//		customerService.save(authenticationDTO);
//		return new Result(true, StatusCode.CREATED, "User Created Successfully!");
//	}

}
