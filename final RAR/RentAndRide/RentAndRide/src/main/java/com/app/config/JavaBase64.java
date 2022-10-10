package com.app.config;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class JavaBase64 {

public String decodePassword(String decPassword) {
		
		return new String(Base64.getMimeDecoder().decode(decPassword));
	}

	public String encodePassword(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());

	}
}
