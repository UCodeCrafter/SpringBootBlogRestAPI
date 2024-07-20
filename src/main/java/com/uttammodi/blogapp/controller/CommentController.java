package com.uttammodi.blogapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.uttammodi.blogapp.payload.CommentDto;
import com.uttammodi.blogapp.service.CommentService;

import jakarta.validation.Valid;
import lombok.val;

@RestController
@RequestMapping("/api/")
public class CommentController {
	private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId") long postId,@Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{postId}/comments")
	    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
	        return commentService.getCommentsByPostId(postId);
	  }
	
	@GetMapping("/posts/{postId}/comments/{id}")
	public CommentDto GetCommentById(@PathVariable(value="postId") Long postId, @PathVariable(value = "id") Long id) {
		return commentService.getCommentById(postId, id);
	}
	
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment( 
			@PathVariable(value="postId") Long postId,
			@PathVariable(value = "id") Long commentId,
			@Valid @RequestBody CommentDto commentDto){
		
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		
		return new ResponseEntity(updatedComment, HttpStatus.OK);
		
	} 
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(
			@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "id") Long id){
		commentService.deleteComment(postId, id);
		return new ResponseEntity<String>("Comment is Deleted Successfully!", HttpStatus.OK);
	}
	


}
