package com.uttammodi.blogapp.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
		description = "PostDto Model Information"
		
		)


public class PostDto {
	private long id;
	
	//Title should not be null or empty
	//Title should have at least 2 characters
	@Schema(
			description = "Blog Post Title"
			)
	@NotEmpty
	@Size(min = 2, message="Post title should atleast 2 characters")
	private String title;
	
	
	//Post description should not be null or empty
	//Post description should have at least 10 characters
	@Schema(
			description = "Blog Post Description"
			)
	@NotEmpty
	@Size(min = 10, message="Post description should atleast 10 characters")
	private String description;
	
	@Schema(
			description = "Blog Post Content"
			)
	@NotEmpty
	@Size(min = 2, message="Must not be empty")
	private String content;
	
	@Schema(
			description = "Blog Post Comments"
			)
	private Set<CommentDto> comments;
	
	@Schema(
			description = "Blog Post Category"
			)
	private Long categoryId;
	

}
