package com.app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.BookCarDTO;

@RestController
@RequestMapping("/util")
@CrossOrigin

public class TestController {

	@GetMapping("/bookDto")
	public BookCarDTO get() {
		return new BookCarDTO();
	}
}
