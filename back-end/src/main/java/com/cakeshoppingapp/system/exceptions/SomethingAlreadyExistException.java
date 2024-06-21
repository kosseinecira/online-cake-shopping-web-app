package com.cakeshoppingapp.system.exceptions;

public class SomethingAlreadyExistException extends RuntimeException {



	/**
	 * 
	 */
	private static final long serialVersionUID = -6314350523145730563L;

	public SomethingAlreadyExistException(String info) {
		super("Already Exist In The Database::: " + info );
	}

}
