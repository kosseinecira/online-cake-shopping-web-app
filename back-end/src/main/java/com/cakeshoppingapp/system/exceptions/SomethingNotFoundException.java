package com.cakeshoppingapp.system.exceptions;

public class SomethingNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1761844056153106600L;

	public SomethingNotFoundException(String info) {
		super("Not Found! ::: " + info);
	}

}
