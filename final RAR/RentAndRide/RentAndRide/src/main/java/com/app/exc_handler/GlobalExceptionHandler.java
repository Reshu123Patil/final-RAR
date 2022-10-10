package com.app.exc_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.custom.excs.AdminException;
import com.app.custom.excs.BookingHandlingException;
import com.app.custom.excs.PasswordLengthException;
import com.app.custom.excs.SignupHandlingException;
import com.app.custom.excs.UserHandlingException;
import com.app.custom.excs.UserNotFoundException;
import com.app.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserHandlingException.class)
	public ResponseEntity<ErrorResponse> handleUserHandlingException(UserHandlingException e){
		System.out.println("in handle user exc");
		return new ResponseEntity<>(new ErrorResponse("Invalid Login",e.getMessage()),
				HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(SignupHandlingException.class)
	public ResponseEntity<ErrorResponse> handleUserHandlingException(SignupHandlingException e){
		System.out.println("in handle user exc");
		return new ResponseEntity<>(new ErrorResponse("User Already Signup",e.getMessage()),
				HttpStatus.ALREADY_REPORTED);
	}
	
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<ErrorResponse> handleUserHandlingException(AdminException e){
		System.out.println("in handle user exc");
		return new ResponseEntity<>(new ErrorResponse("Admin Already Signup",e.getMessage()),
				HttpStatus.ALREADY_REPORTED);
	}
	
	@ExceptionHandler(PasswordLengthException.class)
	public ResponseEntity<ErrorResponse> handleUserHandlingException(PasswordLengthException e){
		System.out.println("in handle user exc");
		return new ResponseEntity<>(new ErrorResponse("Password Length must be greater than 4",e.getMessage()),
				HttpStatus.ALREADY_REPORTED);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserHandlingException(UserNotFoundException e){
		System.out.println("in handle user exc");
		return new ResponseEntity<>(new ErrorResponse("Please Enter Valid Mail",e.getMessage()),
				HttpStatus.ALREADY_REPORTED);
	}
	
	@ExceptionHandler(BookingHandlingException.class)
	public ResponseEntity<ErrorResponse> handleUserHandlingException(BookingHandlingException e){
		System.out.println("in handle user exc");
		return new ResponseEntity<>(new ErrorResponse("Car Already Booked",e.getMessage()),
				HttpStatus.ALREADY_REPORTED);
	}
}
