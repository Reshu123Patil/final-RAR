package com.app.custom.excs;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	public UserNotFoundException(String errMesg) {
		super(errMesg);
	}
}
