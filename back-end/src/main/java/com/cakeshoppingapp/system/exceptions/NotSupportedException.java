package com.cakeshoppingapp.system.exceptions;

public class NotSupportedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3174500105481270237L;

	public NotSupportedException(String info) {
		super("Not Supported! ::: " + info);
	}

}
