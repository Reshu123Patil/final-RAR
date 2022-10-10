package com.app.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.app.pojos.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom.excs.AdminException;
import com.app.custom.excs.BookingHandlingException;
import com.app.custom.excs.PasswordLengthException;
import com.app.custom.excs.SignupHandlingException;
import com.app.custom.excs.UserHandlingException;
import com.app.dao.BookCarRepositoty;
import com.app.dao.CarRepository;
import com.app.dao.UserRepository;
import com.app.dto.BookCarDTO;
import com.app.dto.UserDTO;

@Service
@Transactional 
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookCarRepositoty bookCarRepo;
	
	@Autowired
	private CarRepository carRepo;

	PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	@Autowired
    private JavaMailSender mailSender;
	
	public int result = 0;
	
	@Override
	public User authenticateUser(String email, String password) {
		Optional<User> u = userRepo.findByEmail(email);
		if(u.get() !=null && passwordEncoder.matches(password, u.get().getPassword())) {
			return u.get();
		}else {
		//return null;
		 throw new UserHandlingException("Invalid Credentials!!!!");
		}
	}
	//----------------------------------------------------------------------------------------
	@Override
	public User registerUser(UserDTO userDTO) throws SignupHandlingException, AdminException
	{
		User user = new User();		
		List<User> admincount = userRepo.findByRole(Role.ADMIN);
		System.out.println("========"+admincount+"============");
		BeanUtils.copyProperties(userDTO, user);
		if(userDTO.getEmail().contains ("employee"))
		{				
				user.setRole(Role.EMPLOYEE);			
		}
		else if(userDTO.getEmail().contains("132") && admincount.size()<=1)
		{		
			user.setRole(Role.ADMIN);			
		}
		else if(!userDTO.getEmail().contains("132") || !userDTO.getEmail().contains("employee"))
			user.setRole(Role.USER);
		System.out.println(user);
		System.out.println("========"+admincount+"============");
		if(userDTO.getEmail().contains("132") && admincount.size()>1)
			throw new AdminException("Admin Already Exist...");
		return userRepo.save(user);
	}
	//----------------------------------------------------------------------------------------
	@Override
	public User updateUserProfile(int userId, UserDTO userDTO) throws PasswordLengthException {
		if(userDTO.getPassword().length()<4)
			throw new PasswordLengthException("Password length is small");
		System.out.println("Inside update user profile (Service method)"+userDTO);
		User userDetails = userRepo.findById(userId).get();
		System.out.println("User details from "+userDetails);
		
		System.out.println("========----"+userDTO.getPassword()+"-----===========+++++=");
		System.out.println("========----"+userDetails.getPassword()+"-----===========+++++=");
		//String str1 = passwordEncoder.encode(userDetails.getPassword());
		userDetails.setName(userDTO.getName());
		if((userDTO.getPassword()).equals(userDetails.getPassword())) {
			System.out.println("Nochange");
		}//userDetails.setPassword(userDTO.getPassword());
		else {
		userDetails.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}
		userDetails.setMobile_no(userDTO.getMobile_no());
		System.out.println("Update user details "+ userDetails);

		return userDetails;
	}
	//----------------------------------------------------------------------------------------
	@Override
	public Booking bookCar(BookCarDTO bookCarDto) throws BookingHandlingException {
		
		System.out.println("dto "+bookCarDto);
		Booking carBook = new Booking();
		Booking booking = null;
		boolean ho = isCarAvailable(bookCarDto.getCarid(),bookCarDto.getStart_datetime(),bookCarDto.getEnd_datetime());		
		if(ho==false) { 
		BeanUtils.copyProperties(bookCarDto, carBook);
		System.out.println("after copying"+carBook);
		carBook.setBooking_status(BookingStatus.SUCCESS);
		booking = bookCarRepo.save(carBook);
		
		java.util.Optional<Car> carO = carRepo.findById(bookCarDto.getCarid().getC_id());
		if(carO.isPresent()) {
			Car car = carO.get();
			car.setStatus(CarStatus.AVAILABLE);
			carRepo.save(car);
		}
		}
		return booking;		
	}
	//----------------------------------------------------------------------------------------
	@Override
	public void cancelCarBooking(int bid ) {
		System.out.println("Cancel Car Booking "+bid);
		Optional<Booking> bookOpt = bookCarRepo.findById(bid);
		if(bookOpt.isPresent()) {
			Booking booking = bookOpt.get();
			booking.setBooking_status(BookingStatus.CANCEL);
			bookCarRepo.save(booking);
		}
		Car car=bookCarRepo.findCarByBookingId(bid);
		car.setStatus(CarStatus.AVAILABLE);
	}
	//----------------------------------------------------------------------------------------
	@Override
	public List<Booking> bookingDetails(int userId) {		
	  List<Booking> bookingDetails=bookCarRepo.findByUserId(userId);
		
	  return bookingDetails;		
	}	
	//----------------------------------------------------------------------------------------
	public void sendSimpleEmail(String toEmail, String body, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("rentandride96@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		mailSender.send(message);
		System.out.println("Mail Send...");
	}
	//----------------------------------------------------------------------------------------
	public void sendSimpleEmailtoAdmin(String body, String subject) {
		User u = userRepo.findByRole(Role.ADMIN).get(result);
		String str1 = u.getEmail(); 		
		System.out.println("========="+str1+"----=====");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("rentandride96@gmail.com");
		message.setTo(str1);
		message.setText(body);
		message.setSubject(subject);
		mailSender.send(message);
		System.out.println("Mail Send...");
	}
	//----------------------------------------------------------------------------------------
	public boolean isCarAvailable(Car carid,Date start,Date end) throws BookingHandlingException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String strDate1 = dateFormat. format(start); 
		String strDate2 = dateFormat.format(end);
		int c = carid.getC_id();
		result = bookCarRepo.findAvailable(c,strDate1,strDate2);		
		System.out.println("==============="+result+"===========");
		if(result!=0) {
			throw new BookingHandlingException("Car Already book on that Day!!! Please try another...");
		}
		return false;
	}
	//----------------------------------------------------------------------------------------
	@Override
	public User findByEmail(String email) {
		
		return userRepo.findByEmail(email).get();
	}
	//----------------------------------------------------------------------------------------
	@Override
	public void resetPassword(User user, String password) {
		String encoded = passwordEncoder.encode(password);
		user.setPassword(encoded);
		System.out.println(user);
		userRepo.save(user);		
	}
}