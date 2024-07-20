package com.uttammodi.blogapp.service;

import java.util.List;

import com.uttammodi.blogapp.payload.CommentDto;



public interface CommentService {
	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	CommentDto getCommentById(Long postId, Long CommentId);
	CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest);
	void deleteComment(Long postId,	Long commentId);
}
