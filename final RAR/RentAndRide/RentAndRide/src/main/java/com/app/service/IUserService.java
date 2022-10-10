package com.app.service;

import java.util.List;
import java.util.Optional;

import com.app.custom.excs.AdminException;
import com.app.custom.excs.BookingHandlingException;
import com.app.custom.excs.PasswordLengthException;
import com.app.custom.excs.SignupHandlingException;
import com.app.dto.BookCarDTO;
import com.app.dto.UserDTO;
import com.app.pojos.Booking;
import com.app.pojos.User;

public interface IUserService {
	//Optional<User> authenticateUser(String email, String password);
	
	User authenticateUser(String email, String password);
	
	User registerUser(UserDTO user) throws SignupHandlingException, AdminException;

	User updateUserProfile(int userId,UserDTO userDto) throws PasswordLengthException;
	
	Booking bookCar(BookCarDTO bookCarDto) throws BookingHandlingException;
	
	void cancelCarBooking(int bid);
	
	List<Booking> bookingDetails(int userID);
	
	void sendSimpleEmail(String toEmail,String body,String subject);
	
	void sendSimpleEmailtoAdmin(String body,String subject);

	User findByEmail(String email);

	void resetPassword(User user, String password);
}