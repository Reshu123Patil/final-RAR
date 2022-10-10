package com.app.custom.excs;

public class PasswordLengthException extends Exception {

	private static final long serialVersionUID = 1L;
	public PasswordLengthException(String errMesg) {
		super(errMesg);
	}
}
