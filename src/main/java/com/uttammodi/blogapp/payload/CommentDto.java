package com.uttammodi.blogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
	private long id;
	//name should not be null or empty
	@NotEmpty(message = "Name should not be null or empty")
	private String name;
	
	//email should not be null or empty
	//email field validate
	@NotNull(message = "Email should not be null or empty")
	@Email
	private String email;
	
	//comment body should not be null or empty
	//comment body must have 10  characters
	@NotEmpty
	@Size(min=10, message="Comment body must be minimum 10 characters")
	private String body;
	

}
