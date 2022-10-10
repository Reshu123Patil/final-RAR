package com.app.controller;

import java.util.List;

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


import com.app.dto.CarDTO;
import com.app.dto.LoginRequest;
import com.app.dto.UserDTO;
import com.app.otpGenerator.OtpGenerator;
import com.app.pojos.User;
import com.app.service.EmailService;
import com.app.service.IAdminService;
import com.app.service.ICommonService;
import com.app.service.IFeedbackService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
	@Autowired
	public ICommonService commonService;
	
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	public IFeedbackService feedbackService;
	
PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	@Autowired
	public OtpGenerator otpGenerator;
	
	@Autowired
	public EmailService emailService;
	
	public AdminController() {
		System.out.println("in ctor of " + getClass().getName());
	}
	
	@PostMapping("/login")
    public ResponseEntity<?>authenticateAdmin(@RequestBody LoginRequest request)
    {
		System.out.println("in authenticate admin " + request.getEmail()+ " "+request.getPassword());
		User user = adminService.authenticateAdmin(request.getEmail(), request.getPassword());
		return new ResponseEntity<>(user,HttpStatus.OK);
    }
	
	@PutMapping("/edit_profile")
	public ResponseEntity<?>updateAdminProfile(@RequestParam int adminid, @RequestBody UserDTO userDTo)
	{
		System.out.println("inside update admin profile(Controller method) " + userDTo);
		adminService.updateAdminProfile(adminid, userDTo);
		return new ResponseEntity<>("Admin Profile Updated...!!!",HttpStatus.OK);
	
	}
	
	@PostMapping("/addCar")
	public ResponseEntity<?>addCar(@RequestBody CarDTO carDto)
	{
		System.out.println("Car Details to be Added" + carDto);
		adminService.addCar(carDto);
		return new ResponseEntity<>("Car Details added...!!!",HttpStatus.OK);
	}
	
	@GetMapping("/car_list")
	public ResponseEntity<?> showlist()
	{
		return new ResponseEntity<>(commonService.getAllCarlist(),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCar")
	public ResponseEntity<?>deleteCar(Integer carId)
	{
		System.out.println("Car Details to be Deleted" + carId);
		commonService.deleteCar(carId);
		return new ResponseEntity<>("Car Details deleted...!!!",HttpStatus.OK);
	}
	
	@PostMapping("/resumeCar")
	public ResponseEntity<?>resumeCar(Integer carId)
	{
		System.out.println("inside update car(Controller method) " + carId);
		commonService.resumeCar(carId);
		return new ResponseEntity<>("Car is Resumed Successfully...!!!",HttpStatus.OK);
	
	}
	
	@GetMapping("/feedback_list")
	public ResponseEntity<?> showfeedbacklist()
	{
		return new ResponseEntity<>(feedbackService.getAllFeedbacklist(),HttpStatus.OK);
	}	
	
}