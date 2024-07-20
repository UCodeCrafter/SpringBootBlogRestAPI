package com.uttammodi.blogapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uttammodi.blogapp.payload.JwtAuthResponse;
import com.uttammodi.blogapp.payload.LoginDto;
import com.uttammodi.blogapp.payload.RegisterDto;
import com.uttammodi.blogapp.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private AuthService authService;

	/**
	 * @param authService
	 */
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	//Build login REST API
//	@PostMapping("login")
	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
		String token = authService.login(loginDto);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
		}
	
	//Build Register REST API
	@PostMapping(value= {"/register", "/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String response  = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
