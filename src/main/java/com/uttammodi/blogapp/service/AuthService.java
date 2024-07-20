package com.uttammodi.blogapp.service;

import com.uttammodi.blogapp.payload.LoginDto;
import com.uttammodi.blogapp.payload.RegisterDto;

public interface AuthService {
	String login(LoginDto LoginDto);
	String register(RegisterDto registerDto);
	

}
