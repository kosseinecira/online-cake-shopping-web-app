package com.cakeshoppingapp.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cakeshoppingapp.system.Result;
import com.cakeshoppingapp.system.StatusCode;

@RestControllerAdvice
public class GlobalHandlerAdvice extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8027848182231030401L;

	@ExceptionHandler(SomethingNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Result handleSomethingNotFoundException(SomethingNotFoundException e) {
		return new Result(false, StatusCode.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(SomethingAlreadyExistException.class)
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	public Result handleSomethingNotFoundException(SomethingAlreadyExistException e) {
		return new Result(false, StatusCode.ALREADY_EXIST, e.getMessage());
	}

	@ExceptionHandler(NotSupportedException.class)
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	public Result handleNotSupportedException(NotSupportedException e) {
		return new Result(false, StatusCode.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public Result handleUsernameOrPasswordNotFoundException(Exception e) {
		return new Result(false, StatusCode.UNAUTHORIZED, "Invalid Username or Password", e.getMessage());
	}

}
