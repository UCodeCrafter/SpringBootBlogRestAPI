package com.uttammodi.blogapp.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uttammodi.blogapp.entity.Role;
import com.uttammodi.blogapp.entity.User;
import com.uttammodi.blogapp.exception.BlogAPIException;
import com.uttammodi.blogapp.payload.LoginDto;
import com.uttammodi.blogapp.payload.RegisterDto;
import com.uttammodi.blogapp.repository.RoleRepository;
import com.uttammodi.blogapp.repository.UserRepository;
import com.uttammodi.blogapp.security.JwtTokenProvider;
import com.uttammodi.blogapp.service.AuthService;

@Service
public class AuthServiceImpl  implements AuthService{

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	
	

	/**
	 * @param authenticationManager
	 * @param userRepository
	 * @param roleRepository
	 * @param passwordEncoder
	 */
	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {
		// 
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {
		// Add check for username exists in database
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
		}
		//Add check for email exists in database
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
		}
		
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername()); 
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		userRepository.save(user);
		return "User Registered Sucessfully!";
	}

}
