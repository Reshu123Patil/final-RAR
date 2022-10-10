package com.app.controller;

import javax.mail.MessagingException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.JavaBase64;
import com.app.custom.excs.AdminException;
import com.app.custom.excs.BookingHandlingException;
import com.app.custom.excs.PasswordLengthException;
import com.app.custom.excs.SignupHandlingException;
import com.app.custom.excs.UserNotFoundException;
import com.app.dto.BookCarDTO;
import com.app.dto.LoginRequest;
import com.app.dto.UserDTO;
import com.app.otpGenerator.OtpGenerator;
import com.app.pojos.Feedback;
import com.app.pojos.User;
import com.app.service.EmailService;
import com.app.service.ICommonService;
import com.app.service.IFeedbackService;
import com.app.service.IUserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController 
{
	@Autowired
	public ICommonService commonService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	public IFeedbackService feedbackService;
	
	@Autowired
	public JavaBase64 crypt;
	
	PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	@Autowired
	public OtpGenerator otpGenerator;
	
	@Autowired
	public EmailService emailService;
	
	public UserController() {
		System.out.println("in ctor of " + getClass().getName());
	}

	@PostMapping("/login")
	public ResponseEntity<?>authenticateUser(@RequestBody LoginRequest request){
		System.out.println("in authenticate User"+request.getEmail());		
		User user = userService.authenticateUser(request.getEmail(),request.getPassword());
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	//----------------------------------------------------------------------------------------
	@PostMapping("/signup")
	public ResponseEntity<?>signUpUser(@RequestBody UserDTO userdto) throws MessagingException,SignupHandlingException,AdminException
	{
		try {
		String str = userdto.getEmail();
		String password = userdto.getPassword();
		String encoded = passwordEncoder.encode(userdto.getPassword());
		userdto.setPassword(encoded);
		
		userdto.setEmail(userdto.getEmail().toLowerCase());
		System.out.println("In add user details "+ userdto);
		userService.registerUser(userdto);
		System.out.println("In Successful block");

		if(userdto.getEmail().contains("employee"))
		{System.out.println("In add user details "+ userdto);
			userService.sendSimpleEmailtoAdmin("New Employee Registered Successfully"+"\n"+"Email : "+str+"\n"+"Password : "+password,"Welcome To Rent And Ride services");		
		}
		else
		{
			userService.sendSimpleEmail(str,"You Registered Successfully"+"\n"+"Email : "+str+"\n"+"Password : "+password,"Welcome To Rent And Ride services");
		}
		return new ResponseEntity<>("Registered Successfully...!!!",HttpStatus.OK);
		}
		catch(Exception e){
			System.out.println("In UN---Successful block");
			e.getSuppressed();
			return new ResponseEntity<>("Registered Un-Successfully...!!!",HttpStatus.NOT_FOUND);			
		}
	}
	//----------------------------------------------------------------------------------------
	@PutMapping("/edit_profile")
	public ResponseEntity<?>updateUserProfile(@RequestParam int uid, @RequestBody UserDTO userDTo) throws PasswordLengthException
	{
		System.out.println("inside update user profile(Controller method) " + userDTo);
		userService.updateUserProfile(uid, userDTo);
		return new ResponseEntity<>("User Profile Updated...!!!",HttpStatus.OK);	
	}

	@GetMapping("/car_list")
	public ResponseEntity<?>showlist()
	{
		return new ResponseEntity<>(commonService.getAllCarlist(),HttpStatus.OK);
	}
	//----------------------------------------------------------------------------------------
	@PostMapping("/book_car")
	public ResponseEntity<?>bookCar(@RequestBody BookCarDTO bookCarDTO) throws BookingHandlingException
	{
		try {
		String str = bookCarDTO.getUid().getEmail();
		System.out.println("Booking Car Deatils " + bookCarDTO);
		userService.bookCar(bookCarDTO);
		return new ResponseEntity<>("Car book Successfully!!!",HttpStatus.OK);
		}
		catch(Exception e){
			System.out.println("In UN---Successful block");
			e.getSuppressed();
			return new ResponseEntity<>("Car Book Already on That Day...!!!",HttpStatus.NOT_FOUND);			
		}
	}
	//----------------------------------------------------------------------------------------
	@DeleteMapping("/cancel_booking")
	public ResponseEntity<?>cancelCarBooking(@RequestParam int book_Id )
	{
		System.out.println("Cancel Book Car" + book_Id);
		userService.cancelCarBooking(book_Id);
		return new ResponseEntity<>("Cancel Car Booking...!!!",HttpStatus.OK);
	}
	//----------------------------------------------------------------------------------------
	@PostMapping("/feedback")
	public ResponseEntity<?>feedbackUser(@RequestBody Feedback feedback) 
	{
		System.out.println("in add user feedback " + feedback);
		feedbackService.registerFeedback(feedback);
		return new ResponseEntity<>("FeedBack Submitted...!!!",HttpStatus.OK);
	}
	//----------------------------------------------------------------------------------------
	@GetMapping("/user-booking")
	public ResponseEntity<?> userBookingDetails(@RequestParam int userID)
	{
		System.out.println("in user booking " + userID);
		
		return new ResponseEntity<>(userService.bookingDetails(userID),HttpStatus.OK);
	}
	//----------------------------------------------------------------------------------------
	@PostMapping("/forgetpassword")
	public ResponseEntity<?>forgetPassword(@RequestBody UserDTO userDto) throws MessagingException, UserNotFoundException {	
		UserDTO urDto = null;
		User user = null;
		System.out.print("Sending OTP");
		String otp = otpGenerator.generateOTP();
		emailService.sendOtp(userDto.getEmail(),"OTP: "+otp,"OTP Verification! RentAndRide.com! ");		
		try {
		user =userService.findByEmail(userDto.getEmail());
		if(user == null) {
			throw new UserNotFoundException("Invalid Email input!!!");			
		}
		}
		catch(Exception e){
			System.out.println("In UN---Successful block");
			e.getSuppressed();
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);			
		}
		if(user !=null) {
		urDto = new UserDTO();
		BeanUtils.copyProperties(user, urDto);
		urDto.setSendOtp(otp);
		System.out.print(otp);
		System.out.print(urDto);		
		}
		return  new ResponseEntity<>(urDto,HttpStatus.OK);
	}
	//----------------------------------------------------------------------------------------
	@PutMapping("/resetpassword")
	public ResponseEntity<?>resetPassword(@RequestBody UserDTO userDto) throws MessagingException {	
		User user =userService.findByEmail(userDto.getEmail());
		if(user !=null) {
			userService.resetPassword(user,userDto.getPassword());
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}