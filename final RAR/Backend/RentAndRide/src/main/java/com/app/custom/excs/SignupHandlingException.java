package com.app.custom.excs;

public class SignupHandlingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

		public SignupHandlingException(String message) {
			super(message);
		}
}
