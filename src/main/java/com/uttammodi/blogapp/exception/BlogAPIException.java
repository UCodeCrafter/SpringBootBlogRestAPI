package com.uttammodi.blogapp.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{
	
	private HttpStatus status;
	private String message;
	
	/**
	 * @param status
	 * @param message
	 */
	public BlogAPIException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * @param status
	 * @param message
	 */
	public BlogAPIException(String message, HttpStatus status, String message1) {
		super(message);
		this.status = status;
		this.message = message1;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	

	
	

}
