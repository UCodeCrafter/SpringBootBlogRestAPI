package com.uttammodi.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.CommentToken;

import com.uttammodi.blogapp.entity.Comment;
import com.uttammodi.blogapp.entity.Post;
import com.uttammodi.blogapp.exception.BlogAPIException;
import com.uttammodi.blogapp.exception.ResourceNotFoundException;
import com.uttammodi.blogapp.payload.CommentDto;
import com.uttammodi.blogapp.repository.CommentRepository;
import com.uttammodi.blogapp.repository.PostRepository;
import com.uttammodi.blogapp.service.CommentService;


@Service
public class CommentServiceImpl implements CommentService {
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);

		// retrieve post entity by
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// set post to comment entity
		comment.setPost(post);

		// Comment entity to database
		Comment newComment = commentRepository.save(comment);
		return mapToDTO(newComment);
	}

	

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// Retrieve comments by post id
		List<Comment> comments = commentRepository.findByPostId(postId);

		// Convert list of comment entities to list of comments dto's
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

	}

	@Override
	public CommentDto getCommentById(Long postId, Long CommentId) {
		// Get the particular comment from post id

		// first retrieve from post
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		// Retrieve comment by post id
		Comment comment = commentRepository.findById(CommentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", CommentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post");

		}
		return mapToDTO(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comment entity by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

		}
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());

		Comment updatedComment = commentRepository.save(comment);
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comment entity by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

		}
		commentRepository.delete(comment);
		
	}
	
	
	
	
	
	
	private CommentDto mapToDTO(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
		//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {

		Comment comment = mapper.map(commentDto, Comment.class );
		//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());

		return comment;

	}

}
