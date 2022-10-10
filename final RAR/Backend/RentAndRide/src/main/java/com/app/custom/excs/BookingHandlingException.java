package com.app.custom.excs;

public class BookingHandlingException extends Exception {
	private static final long serialVersionUID = 1L;

	public BookingHandlingException(String message) {
		super(message);
	}
}
