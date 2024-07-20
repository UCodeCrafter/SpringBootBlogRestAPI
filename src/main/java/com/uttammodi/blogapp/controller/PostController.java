package com.uttammodi.blogapp.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uttammodi.blogapp.payload.PostDto;
import com.uttammodi.blogapp.payload.PostDtoV2;
import com.uttammodi.blogapp.payload.PostResponse;
import com.uttammodi.blogapp.service.PostService;
import com.uttammodi.blogapp.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping()
@Tag(
		name = "CRUD REST APIs For Post Resource"
		)
public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	//Create blog post  REST API
	
	@Operation(
			summary = "Create Post REST API",
			description = "Created Post REST api used to save into database"
			
			)
	@ApiResponse(
			responseCode = "201",
			description = "HTTP Status 201 CREATED"
					
			)
	@SecurityRequirement(
			name = "Bearer Authentication"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/v1/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	//Get All Posts REST API
	@Operation(
			summary = "get all Posts REST API",
			description = "get all Posts REST api used fetch from database"
			
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
					
			)
	@GetMapping("/api/v1/posts")
	public PostResponse  getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false ) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
			){
		return (postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
	}
	
	//get "Post by Id" API
	
	@Operation(
			summary = "get Post by Id REST API",
			description = "get Post by id REST api used to get singal Post from database"
			
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
					
			)
	@GetMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDto>  getPostByIdV1(@PathVariable(name="id") long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	@GetMapping("/api/v2/posts/{id}")
	public ResponseEntity<PostDtoV2>  getPostByIdV2(@PathVariable(name="id") long id){
		PostDto postDto = postService.getPostById(id);
		
		PostDtoV2 postDtoV2 = new PostDtoV2();
		
		postDtoV2.setId(postDto.getId());
		postDtoV2.setTitle(postDto.getTitle());
		postDtoV2.setDescription(postDto.getDescription());
		postDtoV2.setContent(postDto.getContent());
		
		List<String> tags = new ArrayList<>();
		tags.addAll(Arrays.asList("Java ", "Spring Boot", "Angular"));
		
		postDtoV2.setTags(tags);
		return ResponseEntity.ok(postDtoV2);
	}
	
	// "Update post by id" REST API
	@Operation(
			summary = "update Post REST API",
			description = "update Post by id REST api used to again save singal Post into database"
			
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
					
			)
	@SecurityRequirement(
			name = "Bearer Authentication"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id){
		PostDto postResponse  = postService.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	// Delete Post REST API
	@Operation(
			summary = "delete Post REST API",
			description = "delete Post by id REST api used to delete singal Post from database"
			
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
					
			)
	@SecurityRequirement(
			name = "Bearer Authentication"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/v1/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
		postService.deletePostById(id);
		return new ResponseEntity<String>("Post entity deleted successfully.", HttpStatus.OK);
		
	}
	
	//Build Get Posts By Category REST API
	@Operation(
			summary = "get all Posts REST API by category",
			description = "get Post by category REST api used get all Posts from database"
			
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
					
			)
	//http://localhost:8080/api/posts/categoy/{id}
	@GetMapping("/api/v1/posts/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId){
		List<PostDto> postDtos =  postService.getPostsByCategory(categoryId);
		
		return ResponseEntity.ok(postDtos);
		
	}
	
}
